package com.example.themlexplorer.ui.authenticationScreens.loginScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themlexplorer.repositorySection.accountRepository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface UserActionOnLoginScreen{
    data class OnEmailIdClick(val emailId: String): UserActionOnLoginScreen
    data class OnPasswordClick(val password: String): UserActionOnLoginScreen
}

@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel() {

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

    fun signInUser(
        inSuccess: () -> Unit,
        inFailure: (String) -> Unit
    ){
        viewModelScope.launch {
            accountRepository.signInUser(
                email = userEmail,
                password = password,
                onSuccess = {
                    inSuccess()
                },
                onFailure = {
                    inFailure(it)
                }
            )
        }
    }

    fun resetStates(){
        userEmail = ""
        password = ""
    }
}