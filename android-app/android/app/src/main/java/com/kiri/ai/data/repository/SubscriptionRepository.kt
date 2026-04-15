package com.kiri.ai.data.repository

import com.kiri.ai.data.models.AuthResponse
import com.kiri.ai.data.models.OrderResponse
import com.kiri.ai.data.remote.SubscriptionApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionRepository @Inject constructor(
    private val subscriptionApi: SubscriptionApi
) {
    suspend fun createOrder(plan: String): Result<OrderResponse> {
        return try {
            val response = subscriptionApi.createOrder(mapOf("plan" to plan))
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                val errorBody = response.errorBody()?.string()
                val message = if (errorBody?.trim()?.startsWith("{") == true) {
                    try { com.google.gson.Gson().fromJson(errorBody, com.kiri.ai.data.models.GenericResponse::class.java).message } catch(e: Exception) { null }
                } else null
                Result.failure(Exception(message ?: "Failed to create order (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verifyPayment(
        orderId: String,
        paymentId: String,
        signature: String,
        plan: String
    ): Result<AuthResponse> {
        return try {
            val request = mapOf(
                "razorpay_order_id" to orderId,
                "razorpay_payment_id" to paymentId,
                "razorpay_signature" to signature,
                "plan" to plan
            )
            val response = subscriptionApi.verifyPayment(request)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.success(body)
            } else {
                val errorBody = response.errorBody()?.string()
                val message = if (errorBody?.trim()?.startsWith("{") == true) {
                    try { com.google.gson.Gson().fromJson(errorBody, com.kiri.ai.data.models.GenericResponse::class.java).message } catch(e: Exception) { null }
                } else null
                Result.failure(Exception(message ?: "Payment verification failed (${response.code()})"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
