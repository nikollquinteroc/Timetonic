package com.example.timetonic.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.timetonic.data.RetrofitServiceFactory
import com.example.timetonic.data.model.login.LoginResponse
import com.example.timetonic.data.repository.login.LoginRepositoryImpl
import com.example.timetonic.domain.login.LoginRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val loginResponse: LoginResponse = LoginResponse(o_u = "", sesskey = ""),
    val error: String = ""
)

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    private val _uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                repository.login(username, password).collect { loginResponse ->
                    _uiState.update { it.copy(isLoading = false, loginResponse = loginResponse) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Network request failed, try again. \n${e.message}"
                    )
                }
            }
        }
    }

    companion object {
        fun provideFactory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(LoginRepositoryImpl(RetrofitServiceFactory.makeRetrofitService())) as T
            }
        }
    }
}