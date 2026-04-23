package com.kiriai.kiriorganization.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kiriai.kiriorganization.data.repository.ChatRepository
import com.kiriai.kiriorganization.utils.NotificationHelper
import com.kiriai.kiriorganization.data.local.NotificationPrefs
import kotlinx.coroutines.flow.first
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ChatPollingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val chatRepository: ChatRepository,
    private val notificationPrefs: NotificationPrefs
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val res = chatRepository.getConversations()
            
            res.onSuccess { conversations ->
                val lastConv = conversations.firstOrNull()
                if (lastConv != null && !lastConv.lastMessage.isNullOrBlank()) {
                    val lastNotifiedId = notificationPrefs.lastNotifiedId.first()
                    
                    // Only notify if this is a DIFFERENT message than the last one we notified about
                    // or if it's from a different conversation.
                    val currentMessageId = "${lastConv.id}_${lastConv.lastMessage.hashCode()}"
                    
                    if (currentMessageId != lastNotifiedId) {
                        NotificationHelper.showResponseNotification(
                            applicationContext,
                            "KIRI // UPDATED",
                            lastConv.lastMessage,
                            lastConv.id
                        )
                        notificationPrefs.saveLastNotifiedId(currentMessageId)
                    }
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
