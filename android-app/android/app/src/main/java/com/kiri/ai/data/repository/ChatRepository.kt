package com.kiri.ai.data.repository

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.kiri.ai.data.models.*
import com.kiri.ai.data.remote.ChatApi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepository @Inject constructor(
    private val chatApi: ChatApi,
    @ApplicationContext private val context: Context
) {
    private val _isConnected = MutableStateFlow(checkInitialConnectivity())
    val isConnected: StateFlow<Boolean> = _isConnected

    private fun checkInitialConnectivity(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetwork ?: return false
        val capabilities = cm.getNetworkCapabilities(activeNetwork) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    init {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        
        connectivityManager.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) { _isConnected.value = true }
            override fun onLost(network: Network) { _isConnected.value = false }
        })
    }

    suspend fun sendMessage(message: String, conversationId: String? = null): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessage(ChatRequest(message, conversationId))
            handleResponse(response)
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("NO_CONNECTIVITY: Checking network status..."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun sendMessageWithFile(
        content: okhttp3.RequestBody,
        conversationId: okhttp3.RequestBody?,
        filePart: okhttp3.MultipartBody.Part
    ): Result<ChatResponse> {
        return try {
            val response = chatApi.sendMessageWithFile(content, conversationId, filePart)
            handleResponse(response)
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("NO_CONNECTIVITY: Upload suspended until network restored."))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun handleResponse(response: retrofit2.Response<ChatResponse>): Result<ChatResponse> {
        val code = response.code()
        val isSuccessful = response.isSuccessful
        android.util.Log.d("Kiri_DEBUG", "ChatRepository: handleResponse code=$code path=${response.raw().request.url.encodedPath}")
        
        return try {
            if (isSuccessful) {
                val body = response.body()
                if (body != null) {
                    android.util.Log.d("Kiri_DEBUG", "ChatRepository: Successful body received")
                    Result.success(body)
                } else {
                    android.util.Log.e("Kiri_DEBUG", "ChatRepository: SERVER_EMPTY_RESPONSE")
                    Result.failure(Exception("SERVER_EMPTY_RESPONSE: The server returned success but no data."))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                android.util.Log.e("Kiri_DEBUG", "ChatRepository: HTTP error $code: $errorBody")
                val parsedError = if (errorBody != null && errorBody.trim().startsWith("{")) {
                    try {
                        com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message
                    } catch (e: Exception) { 
                        android.util.Log.e("Kiri_DEBUG", "ChatRepository: Error parsing error body", e)
                        null 
                    }
                } else null

                val finalMessage = parsedError ?: "HTTP_ERROR_$code: The server is temporarily unavailable."
                Result.failure(Exception(finalMessage))
            }
        } catch (e: Exception) {
            android.util.Log.e("Kiri_DEBUG", "ChatRepository: Critical parsing exception", e)
            Result.failure(Exception("PARSING_ERROR: Unexpected response format from server."))
        }
    }

    suspend fun getConversations(): Result<List<Conversation>> {
        return try {
            val response = chatApi.getConversations()
            if (response.isSuccessful) {
                Result.success(response.body()?.conversations ?: emptyList())
            } else {
                val errorBody = response.errorBody()?.string()
                val message = if (errorBody?.trim()?.startsWith("{") == true) {
                    try { com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message } catch(e: Exception) { null }
                } else null
                Result.failure(Exception(message ?: "Failed to load chats (${response.code()})"))
            }
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("OFFLINE: Please check your internet connection."))
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
                    android.util.Log.d("Kiri_DEBUG", "ChatRepository: Valid detail fetched for id=$id")
                    // Pre-sanitize to prevent crashes in ViewModel/UI
                    val safeMessages = (detail.messages ?: emptyList()).mapIndexed { index, msg ->
                        msg.copy(
                            id = msg.id ?: "remote_${index}",
                            content = msg.content ?: "",
                            role = msg.role ?: "user"
                        )
                    }
                    Result.success(detail.copy(messages = safeMessages))
                } else {
                    android.util.Log.e("Kiri_DEBUG", "ChatRepository: Detail body is null")
                    Result.success(ChatDetail(id = id, title = "Untitled Chat", messages = emptyList()))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                android.util.Log.e("Kiri_DEBUG", "ChatRepository: Detail fetch failed $errorBody")
                val message = if (errorBody?.trim()?.startsWith("{") == true) {
                    try { com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message } catch(e: Exception) { null }
                } else null
                Result.failure(Exception(message ?: "Chat session not found (${response.code()})"))
            }
        } catch (e: java.net.UnknownHostException) {
            Result.failure(Exception("OFFLINE: Message history unavailable."))
        } catch (e: Exception) {
            android.util.Log.e("Kiri_DEBUG", "ChatRepository: Detail fetch exception", e)
            Result.failure(e)
        }
    }

    suspend fun deleteConversation(id: String): Result<GenericResponse> {
        return try {
            val response = chatApi.deleteConversation(id)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                val errorBody = response.errorBody()?.string()
                val message = if (errorBody?.trim()?.startsWith("{") == true) {
                    try { com.google.gson.Gson().fromJson(errorBody, GenericResponse::class.java).message } catch(e: Exception) { null }
                } else null
                Result.failure(Exception(message ?: "Delete failed (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
