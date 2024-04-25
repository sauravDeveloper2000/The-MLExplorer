package com.example.themlexplorer.navigation

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
import com.example.themlexplorer.ui.postAuth.PostAuthScreen

@Composable
fun AppNavigation(
    startingDestination: Destinations,
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

        composable(route = Destinations.PostAuth.route){
            PostAuthScreen(modifier = Modifier.fillMaxSize())
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