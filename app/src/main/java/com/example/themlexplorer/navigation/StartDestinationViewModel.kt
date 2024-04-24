package com.example.themlexplorer.navigation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class StartDestinationViewModel @Inject constructor(): ViewModel() {

    var _startingDestination = MutableStateFlow<Destinations>(Destinations.PreAuth)
        private set

    fun setStartingDestination(
        startingDestination: Destinations
    ){
        _startingDestination.value = startingDestination
    }
}