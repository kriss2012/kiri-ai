package com.kiriai.kiriorganization.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kiriai.kiriorganization.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class InactivityWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val messages = listOf(
            "KIRI // AWAITING_INPUT",
            "SYSTEM_IDLE // RESUME_OPERATIONS?",
            "NEW_INTELLIGENCE_AVAILABLE // ACCESS_NOW",
            "ATELIER_READY // CONTINUE_CREATION",
            "SYNC_LOST // RE-ESTABLISH_LINK"
        )
        
        NotificationHelper.showResponseNotification(
            applicationContext,
            "KIRI // SYSTEM_IDLE",
            messages.random()
        )
        
        return Result.success()
    }
}
