package com.glassheart.fragilefocus.monitoring

import android.app.usage.UsageEvents
import android.app.usage.UsageStatsManager
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * UsageStats轮询器
 * 定期检测当前前台应用
 */
@Singleton
class UsageStatsPoller @Inject constructor(
    private val context: Context
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    
    private val _currentApp = MutableStateFlow<String?>(null)
    val currentApp: StateFlow<String?> = _currentApp.asStateFlow()
    
    private var isPolling = false
    
    /**
     * 开始轮询
     */
    fun startPolling() {
        if (isPolling) return
        isPolling = true
        
        scope.launch {
            while (isActive && isPolling) {
                val foregroundApp = getForegroundApp()
                if (foregroundApp != _currentApp.value && foregroundApp != null) {
                    _currentApp.value = foregroundApp
                }
                delay(POLL_INTERVAL_MS)
            }
        }
    }
    
    /**
     * 停止轮询
     */
    fun stopPolling() {
        isPolling = false
    }
    
    /**
     * 获取当前前台App包名
     */
    private fun getForegroundApp(): String? {
        val time = System.currentTimeMillis()
        val usageEvents = usageStatsManager.queryEvents(time - TIME_WINDOW_MS, time)
        
        var foregroundApp: String? = null
        
        while (usageEvents.hasNextEvent()) {
            val event = UsageEvents.Event()
            usageEvents.getNextEvent(event)
            
            // 查找最新的MOVE_TO_FOREGROUND事件
            if (event.eventType == UsageEvents.Event.MOVE_TO_FOREGROUND) {
                foregroundApp = event.packageName
            }
        }
        
        return foregroundApp
    }
    
    /**
     * 检查是否有UsageStats权限
     */
    fun hasPermission(): Boolean {
        val time = System.currentTimeMillis()
        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            time - 1000 * 10,
            time
        )
        return stats != null && stats.isNotEmpty()
    }
    
    companion object {
        private const val POLL_INTERVAL_MS = 3000L  // 3秒轮询一次
        private const val TIME_WINDOW_MS = 10000L   // 查询过去10秒的事件
    }
}
