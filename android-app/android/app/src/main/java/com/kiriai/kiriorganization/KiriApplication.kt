package com.kiriai.kiriorganization

import android.app.Application
import com.kiriai.kiriorganization.utils.KiriCrashHandler
import com.kiriai.kiriorganization.utils.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import androidx.work.Constraints
import androidx.work.NetworkType
import com.kiriai.kiriorganization.workers.ChatPollingWorker
import com.kiriai.kiriorganization.workers.InactivityWorker
import java.util.concurrent.TimeUnit

import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import javax.inject.Inject

@HiltAndroidApp
class KiriApplication : Application(), Configuration.Provider {
    
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = if (::workerFactory.isInitialized) {
            Configuration.Builder()
                .setWorkerFactory(workerFactory)
                .build()
        } else {
            // Fallback for extremely early wakeups (e.g. OS rescheduling workers before Hilt finishes)
            Configuration.Builder().build()
        }

    override fun onCreate() {
        super.onCreate()
        runCatching { KiriCrashHandler.initialize(this) }
        runCatching { NotificationHelper.createNotificationChannel(this) }
        runCatching { 
            enqueueChatPolling()
            scheduleInactivityNotification()
        }
    }

    private fun scheduleInactivityNotification() {
        val request = androidx.work.OneTimeWorkRequestBuilder<InactivityWorker>()
            .setInitialDelay(6, TimeUnit.HOURS)
            .addTag("inactivity_tag")
            .build()

        WorkManager.getInstance(this).enqueueUniqueWork(
            "inactivity_reminder",
            ExistingPeriodicWorkPolicy.KEEP.let { androidx.work.ExistingWorkPolicy.REPLACE },
            request
        )
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
