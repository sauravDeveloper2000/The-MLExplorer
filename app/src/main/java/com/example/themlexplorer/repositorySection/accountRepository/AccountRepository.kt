package com.example.themlexplorer.repositorySection.accountRepository

interface AccountRepository {
    suspend fun createAccount(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit,
    )

    suspend fun signOutUser()

    suspend fun signInUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    )
}