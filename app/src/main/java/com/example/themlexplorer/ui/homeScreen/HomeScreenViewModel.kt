package com.example.themlexplorer.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themlexplorer.repositorySection.accountRepository.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository
): ViewModel() {

    fun signOutUser(){
        viewModelScope.launch {
            accountRepository.signOutUser()
        }
    }
}