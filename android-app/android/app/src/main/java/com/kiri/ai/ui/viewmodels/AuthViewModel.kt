package com.kiri.ai.ui.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun onNameChange(name: String) { uiState = uiState.copy(name = name) }
    fun onEmailChange(email: String) { uiState = uiState.copy(email = email) }
    fun onPasswordChange(password: String) { uiState = uiState.copy(password = password) }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = authRepository.login(uiState.email, uiState.password)
            uiState = if (result.isSuccess) {
                onSuccess()
                uiState.copy(isLoading = false)
            } else {
                uiState.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = authRepository.register(uiState.name, uiState.email, uiState.password)
            uiState = if (result.isSuccess) {
                onSuccess()
                uiState.copy(isLoading = false)
            } else {
                uiState.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Registration failed")
            }
        }
    }

    fun updateProfile(name: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = authRepository.updateProfile(name)
            uiState = if (result.isSuccess) {
                onSuccess()
                uiState.copy(isLoading = false)
            } else {
                uiState.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Update failed")
            }
        }
    }

    fun logout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout()
            onSuccess()
        }
    }
}

data class AuthUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
