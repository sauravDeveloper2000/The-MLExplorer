package com.example.themlexplorer.navigation

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.themlexplorer.ui.authenticationScreens.loginScreen.LoginScreen
import com.example.themlexplorer.ui.authenticationScreens.registrationScreen.RegistrationScreen
import com.example.themlexplorer.ui.europeLandmarkRecognizer.EuropeLandmarkRecognizer
import com.example.themlexplorer.ui.europeLandmarkRecognizer.domain.Classification
import com.example.themlexplorer.ui.homeScreen.HomeScreen
import com.example.themlexplorer.ui.objectDetection.ObjectDetectionScreen

@Composable
fun AppNavigation(
    startingDestination: Destinations,
    controller: LifecycleCameraController,
    classification: List<Classification>,
    threshold: Float,
    maxResults: Int,
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = startingDestination.route) {
        navigation(
            startDestination = Destinations.PreAuth.LoginScreen.route,
            route = Destinations.PreAuth.route
        ){
            composable(route = Destinations.PreAuth.LoginScreen.route){
                LoginScreen(
                    modifier = Modifier.fillMaxSize(),
                    createAccount = {
                        navigateAndPopUp(
                            navController,
                            desiredNavigationDestination = Destinations.PreAuth.RegistrationScreen.route,
                            popUpDestination = Destinations.PreAuth.LoginScreen.route
                        )
                    }
                )
            }

            composable(route = Destinations.PreAuth.RegistrationScreen.route){
                RegistrationScreen(
                    loginAccount = {
                        navigateAndPopUp(
                            navController,
                            Destinations.PreAuth.LoginScreen.route,
                            Destinations.PreAuth.RegistrationScreen.route
                        )
                    }
                )
            }
        }

        /**
         *  HomeScreen
         */
        composable(route = Destinations.HomeScreen.route) {
            HomeScreen(
                modifier = Modifier.fillMaxSize(),
                navigateToEuropeLandmarkRecognizerScreen = {
                    navController.navigate(Destinations.EuropeLandmarkRecognizerScreen.route)
                },
                navigateToObjectDetectionScreen = {
                    navController.navigate(Destinations.ObjectDetectionAndTrackingScreen.route)
                }
            )
        }

        /**
         *  Euro Landmark Recognition Screen.
         */
        composable(route = Destinations.EuropeLandmarkRecognizerScreen.route) {
            EuropeLandmarkRecognizer(
                modifier = Modifier.fillMaxSize(),
                controller = controller,
                classification = classification
            )
        }

        /**
         * ObjectDetection Screen
         */
        composable(route = Destinations.ObjectDetectionAndTrackingScreen.route) {
            ObjectDetectionScreen(
                modifier = Modifier.fillMaxSize(),
                threshold = threshold,
                maxResults = maxResults
            )
        }
    }
}

/**
 *  In this function we not only navigating to desired destination but also removing the that destination from
 *  which we are navigating.
 */
private fun navigateAndPopUp(
    navController: NavHostController,
    desiredNavigationDestination: String,
    popUpDestination: String
){
    navController.navigate(desiredNavigationDestination){
        popUpTo(popUpDestination){
            inclusive = true
        }
    }
}