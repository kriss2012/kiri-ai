package com.kiri.ai.ui.viewmodels

/**
 * ====================================================================================
 * STABILITY_ARCHITECTURE_NOTICE
 * ====================================================================================
 * 
 * ERROR_FIXED: SnapshotStateObserver Crash - 2026-04-15
 * 
 * ARCHITECTURAL_VIOLATION_CORRECTED:
 * ----------------------------------
 * Previously this ViewModel used 'mutableStateOf()' which is COMPOSE-ONLY API.
 * This caused crashes during authentication flows when state was accessed outside
 * composition (e.g., during network callbacks, background threads).
 * 
 * CORRECT_PATTERN_IMPLEMENTED:
 * ----------------------------
 * Now using StateFlow:
 *   - MutableStateFlow for internal updates (thread-safe)
 *   - StateFlow for external observation (lifecycle-aware)
 *   - _uiState.update { } for atomic state mutations
 * 
 * DO_NOT_CHANGE: Reverting to mutableStateOf will reintroduce crashes.
 * ====================================================================================
 */

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kiri.ai.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) { _uiState.update { it.copy(name = name) } }
    fun onEmailChange(email: String) { _uiState.update { it.copy(email = email) } }
    fun onPasswordChange(password: String) { _uiState.update { it.copy(password = password) } }

    fun login(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.login(currentState.email, currentState.password)
            if (result.isSuccess) {
                onSuccess()
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Login failed") }
            }
        }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val currentState = _uiState.value
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.register(currentState.name, currentState.email, currentState.password)
            if (result.isSuccess) {
                onSuccess()
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Registration failed") }
            }
        }
    }

    fun updateProfile(name: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val result = authRepository.updateProfile(name)
            if (result.isSuccess) {
                onSuccess()
                _uiState.update { it.copy(isLoading = false) }
            } else {
                _uiState.update { it.copy(isLoading = false, error = result.exceptionOrNull()?.message ?: "Update failed") }
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
