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
                LoginScreen(modifier = Modifier.fillMaxSize())
            }

            composable(route = Destinations.PreAuth.RegistrationScreen.route){
                RegistrationScreen()
            }
        }

        composable(route = Destinations.PostAuth.route){
            PostAuthScreen(modifier = Modifier.fillMaxSize())
        }
    }
}