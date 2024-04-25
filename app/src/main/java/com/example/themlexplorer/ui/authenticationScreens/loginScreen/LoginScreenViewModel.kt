package com.example.themlexplorer.ui.authenticationScreens.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

sealed interface UserActionOnLoginScreen{
    data class OnEmailIdClick(val emailId: String): UserActionOnLoginScreen
    data class OnPasswordClick(val password: String): UserActionOnLoginScreen
}

@HiltViewModel
class LoginScreenViewModel @Inject constructor(): ViewModel() {

    var userEmail by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set

    fun updateStates(event: UserActionOnLoginScreen){
        when(event){
            is UserActionOnLoginScreen.OnEmailIdClick -> this.userEmail = event.emailId
            is UserActionOnLoginScreen.OnPasswordClick -> this.password = event.password
        }
    }
}