package com.example.camellando.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.camellando.ui.auth.*
import com.example.camellando.ui.feed.*
import com.example.camellando.ui.search.*
import com.example.camellando.ui.profile.*
import com.example.camellando.ui.chat.*

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) { LoginScreen() }
        composable(Screen.Register.route) { RegisterScreen() }
        composable(Screen.ServiceProfileSetup.route) { ServiceProfileSetupScreen() }
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.CreatePost.route) { CreatePostScreen() }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.ProviderProfile.route) { ProviderProfileScreen() }
        composable(Screen.ClientProfile.route) { ClientProfileScreen() }
        composable(Screen.Chat.route) { ChatScreen() }
    }
}