package com.example.themlexplorer.di

import com.example.themlexplorer.repositorySection.accountRepository.AccountRepository
import com.example.themlexplorer.repositorySection.accountRepository.AccountRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesAccountRepository(firebaseAuth: FirebaseAuth): AccountRepository =
        AccountRepositoryImpl(firebaseAuth = firebaseAuth)
}