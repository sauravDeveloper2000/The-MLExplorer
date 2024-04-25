package com.example.themlexplorer.ui.postAuth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PostAuthScreen(
    modifier: Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel()
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Post Auth")
        Button(onClick = {
            homeScreenViewModel.signOutUser()
        }) {
            Text(text = "Sign-Out")
        }
    }
}