package com.example.camellando.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.camellando.ui.auth.*
import com.example.camellando.ui.chat.*
import com.example.camellando.ui.feed.*
import com.example.camellando.ui.negotiation.*
import com.example.camellando.ui.profile.*
import com.example.camellando.ui.search.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    //cuando este la pantalla de inicio cambiar esta linea
                    //navController.navigate(Screen.Home.route)
                    navController.navigate(Screen.ProviderProfile.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.Register.route) { RegisterScreen() }
        composable(Screen.ServiceProfileSetup.route) {
            ServiceProfileSetupScreen(
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Register.route)
                    }
                },
                onRegistrarClick = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(Screen.CreateOffer.route) { CreateOfferDialog() }
        composable(Screen.ProviderProfile.route) { ProviderProfileScreen() }
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.CreatePost.route) { CreatePostScreen() }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.ProviderDetail.route) { ProviderDetailScreen() }
        composable(Screen.OfferReceived.route) { OfferReceivedDialog() }
        composable(Screen.CounterOffer.route) { CounterOfferDialog() }
        composable(Screen.ClientProfile.route) { ClientProfileScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
        composable(Screen.AgreementReached.route) { AgreementReachedDialog() }
    }
}