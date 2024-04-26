package com.example.themlexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.themlexplorer.navigation.AppNavigation
import com.example.themlexplorer.navigation.Destinations
import com.example.themlexplorer.navigation.StartDestinationViewModel
import com.example.themlexplorer.ui.theme.TheMLExplorerTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private val startDestinationViewModel: StartDestinationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = Firebase.auth
        setContent {
            TheMLExplorerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val startDestination by startDestinationViewModel._startingDestination.collectAsState()
                    AppNavigation(startingDestination = startDestination)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener {
            val currentUser = it.currentUser
            if (currentUser != null){
                startDestinationViewModel._startingDestination.value = Destinations.HomeScreen
            } else{
                startDestinationViewModel._startingDestination.value = Destinations.PreAuth
            }
        }
    }
}
