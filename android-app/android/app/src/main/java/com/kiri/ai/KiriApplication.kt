package com.kiri.ai

import android.app.Application
import com.kiri.ai.utils.KiriCrashHandler
import com.kiri.ai.utils.NotificationHelper
import dagger.hilt.android.HiltAndroidApp

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import javax.inject.Inject

@HiltAndroidApp
class KiriApplication : Application(), Configuration.Provider {
    
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        KiriCrashHandler.initialize(this)
        NotificationHelper.createNotificationChannel(this)
    }
}
