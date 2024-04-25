package com.example.themlexplorer.repositorySection.accountRepository

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AccountRepository {

    override suspend fun createAccount(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(it.exception.toString())
                }
            }
    }

    override suspend fun signOutUser() {
        firebaseAuth.signOut()
    }

    override suspend fun signInUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        firebaseAuth.signInWithEmailAndPassword(
            email,
            password
        ).addOnCompleteListener {
            if (it.isSuccessful){
                onSuccess()
            } else {
                onFailure(it.exception.toString())
            }
        }
    }
}