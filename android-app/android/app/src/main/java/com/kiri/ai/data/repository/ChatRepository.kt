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
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val conversationsRaw = body["conversations"] as? List<Map<String, Any>>
                val conversations = conversationsRaw?.map { map ->
                    Conversation(
                        id = map["id"] as String,
                        title = map["title"] as String,
                        model = map["model"] as String,
                        isPinned = map["isPinned"] as? Boolean ?: false,
                        messageCount = (map["messageCount"] as? Double)?.toInt() ?: 0,
                        lastMessage = map["lastMessage"] as? String ?: "",
                        updatedAt = map["updatedAt"] as String
                    )
                } ?: emptyList()
                Result.success(conversations)
            } else {
                Result.failure(Exception(response.message()))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getConversationDetail(id: String): Result<ChatDetail> {
        return try {
            val response = chatApi.getConversation(id)
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()!!
                val convMap = body["conversation"] as Map<String, Any>
                val messagesRaw = convMap["messages"] as List<Map<String, Any>>
                val messages = messagesRaw.map { msg ->
                    ChatMessage(
                        role = msg["role"] as String,
                        content = msg["content"] as String
                    )
                }
                Result.success(
                    ChatDetail(
                        id = convMap["_id"] as String,
                        title = convMap["title"] as String,
                        messages = messages
                    )
                )
            } else {
                Result.failure(Exception(response.message()))
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
