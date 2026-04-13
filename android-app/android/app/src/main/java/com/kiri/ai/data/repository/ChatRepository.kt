package com.kiri.ai.data.repository

import com.kiri.ai.data.models.*
import com.kiri.ai.data.remote.ChatApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatApi: ChatApi
) {
    suspend fun sendMessage(message: String, conversationId: String? = null): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessage(ChatRequest(message, conversationId))
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversations(): Result<List<Conversation>> {
        return try {
            val response = chatApi.getConversations()
            if (response.isSuccessful) {
                Result.success(response.body()?.conversations ?: emptyList())
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversationDetail(id: String): Result<ChatDetail> {
        return try {
            val response = chatApi.getConversation(id)
            if (response.isSuccessful) {
                val detail = response.body()?.conversation
                if (detail != null) {
                    Result.success(detail)
                } else {
                    Result.failure(Exception("Conversation detail is empty"))
                }
            } else {
                Result.failure(Exception("Error ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteConversation(id: String): Result<GenericResponse> {
        return try {
            val response = chatApi.deleteConversation(id)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
