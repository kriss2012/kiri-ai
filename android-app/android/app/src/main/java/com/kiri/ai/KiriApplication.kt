package com.kiri.ai

import android.app.Application
import com.kiri.ai.utils.KiriCrashHandler
import com.kiri.ai.utils.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class KiriApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KiriCrashHandler.initialize(this)
        NotificationHelper.createNotificationChannel(this)
    }
}
