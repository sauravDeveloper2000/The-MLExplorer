package com.example.themlexplorer.navigation

sealed class Destinations(val route: String) {

    data object PreAuth: Destinations(route = "PreAuth"){
        data object LoginScreen: Destinations(route = "LoginScreen")
        data object RegistrationScreen: Destinations(route = "RegistrationScreen")
    }

    data object HomeScreen : Destinations(route = "HomeScreen")
    data object EuropeLandmarkRecognizerScreen :
        Destinations(route = "EuropeLandmarkRecognizerScreen")
}