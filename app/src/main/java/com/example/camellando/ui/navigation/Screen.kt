package com.example.camellando.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ServiceProfileSetup : Screen("service_profile_setup")
    object Home : Screen("home")
    object CreatePost : Screen("create_post")
    object Search : Screen("search")
    object ProviderProfile : Screen("provider_profile")
    object ClientProfile : Screen("client_profile")
    object Chat : Screen("chat")
}
