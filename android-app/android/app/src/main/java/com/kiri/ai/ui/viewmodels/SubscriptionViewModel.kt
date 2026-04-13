package com.kiri.ai.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.local.AuthDataStore
import com.kiri.ai.data.repository.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SubscriptionUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val orderData: Map<String, Any>? = null,
    val success: Boolean = false,
    val pendingPlan: String? = null
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val repository: SubscriptionRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    var uiState by mutableStateOf(SubscriptionUiState())
        private set

    fun createOrder(plan: String) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null, pendingPlan = plan)
            repository.createOrder(plan).onSuccess {
                uiState = uiState.copy(isLoading = false, orderData = it)
            }.onFailure {
                uiState = uiState.copy(isLoading = false, error = it.message)
            }
        }
    }

    fun onPaymentSuccess(orderId: String, paymentId: String, signature: String) {
        val plan = uiState.pendingPlan ?: "premium"
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            repository.verifyPayment(orderId, paymentId, signature, plan).onSuccess {
                authDataStore.saveUser(it.user)
                uiState = uiState.copy(isLoading = false, success = true)
            }.onFailure {
                uiState = uiState.copy(isLoading = false, error = "Payment verification failed: ${it.message}")
            }
        }
    }

    fun clearError() {
        uiState = uiState.copy(error = null)
    }
}
