package com.example.themlexplorer.ui.homeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.themlexplorer.R
import com.example.themlexplorer.components.VerticalSpace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    homeScreenViewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToEuropeLandmarkRecognizerScreen: () -> Unit,
    navigateToObjectDetectionScreen: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            })
        }
    ) {
        Column(
            modifier = modifier.padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            /**
             * Europe Landmark Recognition
             */
            Text(
                text = "Explore Europe Landmark Recognition",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                shape = RoundedCornerShape(20),
                onClick = navigateToEuropeLandmarkRecognizerScreen
            ) {
                Text(text = "Explore")
            }

            VerticalSpace(space = 20)

            /**
             *  Object Detection and Tracking
             */
            Text(
                text = "Explore Object Detection and Tracking",
                style = MaterialTheme.typography.bodyMedium
            )
            Button(
                shape = RoundedCornerShape(20),
                onClick = navigateToObjectDetectionScreen
            ) {
                Text(text = "Explore")
            }

            VerticalSpace(space = 20)


            Button(
                shape = RoundedCornerShape(20),
                onClick = {
                    homeScreenViewModel.signOutUser()
                }
            ) {
                Text(text = "Sign-Out")
            }
        }
    }
}