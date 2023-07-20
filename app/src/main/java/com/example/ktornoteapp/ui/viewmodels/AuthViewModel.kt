package com.example.ktornoteapp.ui.viewmodels

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ktornoteapp.data.api.NoteApi
import com.example.ktornoteapp.data.models.Token
import com.example.ktornoteapp.data.models.params.LoginParams
import com.example.ktornoteapp.data.models.params.RegistrationParams
import com.example.ktornoteapp.data.repositories.UserRepository
import com.example.ktornoteapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
   private val userRepository: UserRepository
): ViewModel() {

    private val _loginState = MutableSharedFlow<Resource<Token>>()
    val loginState = _loginState.asSharedFlow()

    private val _registrationState = MutableSharedFlow<Resource<Token>>()
    val registrationState = _registrationState.asSharedFlow()

    private val _currentAuthState = MutableSharedFlow<Resource<Token>>()
    val currentAuthState = _currentAuthState.asSharedFlow()

    fun login(loginParams: LoginParams) = viewModelScope.launch {
        _loginState.emit(Resource.Loading())

        if(loginParams.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(loginParams.email).matches()) {
            _loginState.emit(Resource.Error("Email isn't valid"))
            return@launch
        }
        if(loginParams.password.length < 6) {
            _loginState.emit(Resource.Error("Password must be at least 6 characters"))
            return@launch
        }

        _loginState.emit(userRepository.login(param = loginParams))
    }

    fun registration(registrationParams: RegistrationParams, repeatPassword: String) = viewModelScope.launch {
        _registrationState.emit(Resource.Loading())

        if(registrationParams.email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(registrationParams.email).matches()) {
            _registrationState.emit(Resource.Error("Email isn't valid"))
            return@launch
        }
        if(registrationParams.password != repeatPassword) {
            _registrationState.emit(Resource.Error("Passwords don't match"))
            return@launch
        }
        if(registrationParams.password.length < 6) {
            _registrationState.emit(Resource.Error("Password must be at least 6 characters"))
            return@launch
        }

        _registrationState.emit(userRepository.registration(param = registrationParams))
    }

    fun getToken() = viewModelScope.launch {
        _currentAuthState.emit(userRepository.getToken())
    }

    fun logout() = viewModelScope.launch {
       userRepository.logout()
    }

}