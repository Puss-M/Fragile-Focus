package com.glassheart.fragilefocus.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.glassheart.fragilefocus.service.MonitorService

/**
 * 开机启动接收器
 * 系统启动后自动启动监控服务
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // 启动监控服务
            MonitorService.start(context)
        }
    }
}
