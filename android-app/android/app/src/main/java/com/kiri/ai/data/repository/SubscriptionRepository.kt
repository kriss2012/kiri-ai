package com.kiri.ai.data.repository

import com.kiri.ai.data.models.AuthResponse
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
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception(response.message()))
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
