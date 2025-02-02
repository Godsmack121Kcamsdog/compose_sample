package com.kucherenko.ronis.compose_app.vm

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val minPasswordLength = 6

    private val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    var emailValue by mutableStateOf("")
        private set

    var passwordValue by mutableStateOf("")
        private set

    fun setPassword(value: String) {
        passwordValue = value
    }

    fun setEmail(value: String) {
        emailValue = value
    }

    private fun validateEmail(): Boolean {
        val email = emailValue.trim()
        var isValid = true
        var errorMessage = ""
        if (email.isBlank() || email.isEmpty()) {
            errorMessage = "Please fill email field"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            errorMessage = "Wrong email Format"
            isValid = false
        }
        if (!isValid) {
            viewModelScope.launch {
                _events.emit(Event.LoginError(errorMessage))
            }
        }
        return isValid
    }

    private fun validatePassword(): Boolean {
        val password = passwordValue.trim()
        var isValid = true
        var errorMessage = ""

        if (password.isBlank() || password.isEmpty()) {
            errorMessage = "Please fill password field"
            isValid = false
        } else if (password.length < minPasswordLength) {
            errorMessage = "Password must more than $minPasswordLength character"
            isValid = false
        }
        if (!isValid) {
            viewModelScope.launch {
                _events.emit(Event.LoginError(errorMessage))
            }
        }
        return isValid
    }

    fun validateForm() {
        if (validateEmail() && validatePassword()) {
            viewModelScope.launch {
                //todo request to server
                _events.emit(Event.LoginSuccess)
            }
        }
    }

    sealed class Event {
        data object LoginSuccess : Event()
        data class LoginError(val errorMsg: String) : Event()
    }
}