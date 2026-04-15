package com.kiri.ai.data.remote

import com.kiri.ai.data.models.*
import retrofit2.Response
import retrofit2.http.*

interface AuthApi {
    @POST("auth/register")
    suspend fun register(@Body request: Map<String, String>): Response<AuthResponse>

    @POST("auth/login")
    suspend fun login(@Body request: Map<String, String>): Response<AuthResponse>

    @GET("auth/me")
    suspend fun getMe(): Response<AuthResponse>

    @PUT("auth/profile")
    suspend fun updateProfile(@Body request: Map<String, String>): Response<AuthResponse>

    @PUT("auth/change-password")
    suspend fun changePassword(@Body request: Map<String, String>): Response<GenericResponse>
}

interface SubscriptionApi {
    @POST("subscription/create-order")
    suspend fun createOrder(@Body request: Map<String, String>): Response<OrderResponse>

    @POST("subscription/verify-payment")
    suspend fun verifyPayment(@Body request: Map<String, Any>): Response<AuthResponse>

    @GET("subscription/status")
    suspend fun getStatus(): Response<Map<String, Any>>
}

interface ChatApi {
    @GET("chat/conversations")
    suspend fun getConversations(): Response<ConversationsResponse>

    @GET("chat/conversations/{id}")
    suspend fun getConversation(@Path("id") id: String): Response<ConversationDetailResponse>

    @POST("chat/message")
    suspend fun sendMessage(@Body request: ChatRequest): Response<ChatResponse>

    @Multipart
    @POST("chat/message/upload")
    suspend fun sendMessageWithFile(
        @Part("content") content: okhttp3.RequestBody,
        @Part("conversationId") conversationId: okhttp3.RequestBody?,
        @Part file: okhttp3.MultipartBody.Part
    ): Response<ChatResponse>

    @DELETE("chat/conversations/{id}")
    suspend fun deleteConversation(@Path("id") id: String): Response<GenericResponse>

    @DELETE("chat/conversations")
    suspend fun deleteAllConversations(): Response<GenericResponse>
}
