package com.example.themlexplorer.ui.authenticationScreens.registrationScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 *  So we are handling possible user action on registration screen
 *  through sealed interface concept.
 */
sealed interface UserActionOnRegistrationScreen{
    data class OnEmailIdClick(val emailId: String): UserActionOnRegistrationScreen
    data class OnNewPasswordClick(val newPassword: String): UserActionOnRegistrationScreen
    data class OnConfirmPasswordClick(val confirmPassword: String): UserActionOnRegistrationScreen
}

@HiltViewModel
class RegistrationScreenViewModel @Inject constructor() : ViewModel() {

    var userEmail by mutableStateOf("")
        private set
    var newPassword by mutableStateOf("")
        private set
    var confirmPassword by mutableStateOf("")
        private set

    fun updateStates(event: UserActionOnRegistrationScreen){
        when(event){
            is UserActionOnRegistrationScreen.OnConfirmPasswordClick -> this.confirmPassword = event.confirmPassword
            is UserActionOnRegistrationScreen.OnEmailIdClick -> this.userEmail = event.emailId
            is UserActionOnRegistrationScreen.OnNewPasswordClick -> this.newPassword = event.newPassword
        }
    }
}