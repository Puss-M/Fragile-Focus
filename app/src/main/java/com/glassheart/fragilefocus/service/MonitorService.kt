package com.glassheart.fragilefocus.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.glassheart.fragilefocus.MainActivity
import com.glassheart.fragilefocus.R
import com.glassheart.fragilefocus.domain.PetManager
import com.glassheart.fragilefocus.monitoring.UsageStatsPoller
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 监控前台服务
 * 保持后台运行，持续监控用户的应用使用情况
 */
class MonitorService : Service() {
    
    @Inject
    lateinit var usageStatsPoller: UsageStatsPoller
    
    @Inject
    lateinit var petManager: PetManager
    
    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    override fun onCreate() {
        super.onCreate()
        // TODO: 依赖注入初始化（需要Hilt/Dagger）
        // 暂时先手动创建实例来演示逻辑
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 创建前台通知
        val notification = createNotification()
        startForeground(NOTIFICATION_ID, notification)
        
        // 启动监控
        startMonitoring()
        
        return START_STICKY // 被杀后自动重启
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    override fun onDestroy() {
        super.onDestroy()
        usageStatsPoller.stopPolling()
        serviceScope.cancel()
    }
    
    /**
     * 启动应用监控
     */
    private fun startMonitoring() {
        // 启动轮询
        usageStatsPoller.startPolling()
        
        // 监听前台App变化
        serviceScope.launch {
            usageStatsPoller.currentApp.collectLatest { packageName ->
                packageName?.let {
                    petManager.onAppChanged(it)
                }
            }
        }
    }
    
    /**
     * 创建前台通知
     */
    private fun createNotification(): Notification {
        createNotificationChannel()
        
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("守护中")
            .setContentText("正在守护你的专注...")
            .setSmallIcon(R.drawable.ic_notification) // TODO: 添加通知图标
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
    }
    
    /**
     * 创建通知渠道 (Android 8.0+)
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "专注守护",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "保持应用在后台运行，守护你的宠物"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    companion object {
        private const val NOTIFICATION_ID = 1001
        private const val CHANNEL_ID = "monitor_service_channel"
        
        /**
         * 启动监控服务
         */
        fun start(context: Context) {
            val intent = Intent(context, MonitorService::class.java)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(intent)
            } else {
                context.startService(intent)
            }
        }
        
        /**
         * 停止监控服务
         */
        fun stop(context: Context) {
            val intent = Intent(context, MonitorService::class.java)
            context.stopService(intent)
        }
    }
}
