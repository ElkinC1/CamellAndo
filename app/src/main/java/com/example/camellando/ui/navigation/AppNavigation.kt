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
        composable(Screen.Login.route) { LoginScreen() }
        composable(Screen.Register.route) { RegisterScreen() }
        composable(Screen.ServiceProfileSetup.route) { ServiceProfileSetupScreen() }
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