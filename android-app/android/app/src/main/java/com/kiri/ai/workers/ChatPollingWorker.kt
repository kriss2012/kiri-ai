package com.kiri.ai.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.kiri.ai.data.repository.ChatRepository
import com.kiri.ai.utils.NotificationHelper
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ChatPollingWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val chatRepository: ChatRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Simple polling logic: Check for new conversations/messages
        return try {
            // Note: In a real app, you'd compare the last known message ID
            // For now, we'll fetch conversations and if there's a recent one, notify
            val res = chatRepository.getConversations()
            
            res.onSuccess { conversations ->
                val lastConv = conversations.firstOrNull()
                if (lastConv != null && !lastConv.lastMessage.isNullOrBlank()) {
                    NotificationHelper.showResponseNotification(
                        applicationContext,
                        "KIRI // UPDATED",
                        lastConv.lastMessage
                    )
                }
            }
            
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
