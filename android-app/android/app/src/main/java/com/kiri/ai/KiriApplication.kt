package com.kiri.ai

import android.app.Application
import com.kiri.ai.utils.KiriCrashHandler
import com.kiri.ai.utils.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.Constraints
import androidx.work.NetworkType
import com.kiri.ai.workers.ChatPollingWorker
import java.util.concurrent.TimeUnit

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
        enqueueChatPolling()
    }

    private fun enqueueChatPolling() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val request = PeriodicWorkRequestBuilder<ChatPollingWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "chat_polling",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
