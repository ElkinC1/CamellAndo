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

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen()
        }

        composable(Screen.ServiceProfileSetup.route) {
            ServiceProfileSetupScreen()
        }

        composable(Screen.CreateOffer.route) {
            CreateOfferDialog()
        }

        composable(Screen.ProviderProfile.route) {
            ProviderProfileScreen()
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCreatePost = {
                    navController.navigate(Screen.CreatePost.route)
                },
                onNavigateToDetail = { _ ->
                    navController.navigate(Screen.ProviderDetail.route)
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.ClientProfile.route)
                }
            )
        }

        composable(Screen.CreatePost.route) {
            CreatePostScreen()
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNavigateHome = {
                    navController.navigate(Screen.Home.route)
                },
                onNavigateMessages = {
                    navController.navigate(Screen.Chat.route)
                },
                onNavigateProfile = {
                    navController.navigate(Screen.ClientProfile.route)
                },
                onNavigateMenu = {
                    navController.navigate(Screen.ClientProfile.route)
                },
                onProviderClick = {
                    navController.navigate(Screen.ProviderDetail.route)
                }
            )
        }

        composable(Screen.ProviderDetail.route) {
            ProviderDetailScreen()
        }

        composable(Screen.OfferReceived.route) {
            OfferReceivedDialog()
        }

        composable(Screen.CounterOffer.route) {
            CounterOfferDialog()
        }

        composable(Screen.ClientProfile.route) {
            ClientProfileScreen(
                onBack = {
                    navController.popBackStack()
                },
                onViewDetail = {
                    navController.navigate(Screen.ProviderDetail.route)
                }
            )
        }

        composable(Screen.Chat.route) {
            ChatScreen()
        }

        composable(Screen.AgreementReached.route) {
            AgreementReachedDialog()
        }
    }
}