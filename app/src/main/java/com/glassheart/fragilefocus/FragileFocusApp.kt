package com.glassheart.fragilefocus

import android.app.Application

/**
 * Application入口
 * 初始化全局依赖（DI等）
 */
class FragileFocusApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // TODO: 初始化Hilt/Dagger (依赖注入)
        // TODO: 初始化Supabase客户端
    }
}
