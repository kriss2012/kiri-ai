package com.kiri.ai.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.local.AuthDataStore
import com.kiri.ai.data.repository.SubscriptionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

import com.kiri.ai.data.models.OrderResponse

data class SubscriptionUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val orderData: OrderResponse? = null,
    val success: Boolean = false,
    val pendingPlan: String? = null
)

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val repository: SubscriptionRepository,
    private val authDataStore: AuthDataStore
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionUiState())
    val uiState: StateFlow<SubscriptionUiState> = _uiState.asStateFlow()

    fun createOrder(plan: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, pendingPlan = plan) }
            repository.createOrder(plan).onSuccess { data ->
                _uiState.update { it.copy(isLoading = false, orderData = data) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message ?: "Failed to create order") }
            }
        }
    }

    fun onPaymentSuccess(orderId: String, paymentId: String, signature: String) {
        val plan = _uiState.value.pendingPlan ?: "premium"
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            repository.verifyPayment(orderId, paymentId, signature, plan).onSuccess { data ->
                authDataStore.saveUser(data.user)
                _uiState.update { it.copy(isLoading = false, success = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = "Payment verification failed: ${error.message}") }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
