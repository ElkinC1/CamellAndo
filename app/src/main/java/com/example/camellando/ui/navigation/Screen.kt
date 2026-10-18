package com.example.camellando.ui.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object ServiceProfileSetup : Screen("service_profile_setup")
    object CreateOffer : Screen("create_offer")
    object ProviderProfile : Screen("provider_profile")
    object Home : Screen("home")
    object CreatePost : Screen("create_post")
    object Search : Screen("search")
    object ProviderDetail : Screen("provider_detail")
    object OfferReceived : Screen("offer_received")
    object CounterOffer : Screen("counter_offer")
    object ClientProfile : Screen("client_profile")
    object Chat : Screen("chat")
    object AgreementReached : Screen("agreement_reached")
}