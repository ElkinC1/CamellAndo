package com.example.camellando.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.camellando.ui.auth.LoginScreen // O tu componente de Iniciar Sesión
import com.example.camellando.ui.auth.RegisterScreen
import com.example.camellando.ui.feed.HomeScreen
import com.example.camellando.ui.negotiation.AgreementReachedDialog
import com.example.camellando.ui.negotiation.OfferReceivedDialog

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    // 1. EL DESTINO INICIAL AHORA ES LOGIN
    startDestination: String = Screen.Login.route
) {
    var showOfferReceivedDialog by remember { mutableStateOf(false) }
    var showAgreementReachedDialog by remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // PANTALLA 1: INICIAR SESIÓN (Pantalla Inicial)
        composable(Screen.Login.route) {
            // Reemplaza LoginScreen con el nombre exacto de la función composable de tu Login
            LoginScreen(
                onLoginSuccess = {
                    // Al iniciar sesión exitosamente, se navega al Home y se elimina Login del historial
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // PANTALLA 2: REGISTRO
        // PANTALLA: REGISTRO
        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterClientSuccess = { mensaje ->
                    // Al registrarse como cliente, redirige al Login limpiando el registro
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onRegisterProviderSuccess = {
                    // Redirige a la pantalla para configurar el perfil de prestador de servicios
                    navController.navigate(Screen.ServiceProfileSetup.route)
                }
            )
        }

        // PANTALLA 3: HOME / FEED
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCreatePost = {
                    navController.navigate(Screen.CreatePost.route)
                },
                onNavigateToDetail = { servicioId ->
                    showOfferReceivedDialog = true
                },
                onNavigateBottomTab = { targetScreen ->
                    if (targetScreen.route != Screen.Home.route) {
                        navController.navigate(targetScreen.route) {
                            popUpTo(Screen.Home.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )

            // Modales de negociación sobre el Home
            if (showOfferReceivedDialog) {
                OfferReceivedDialog(
                    onDismiss = { showOfferReceivedDialog = false },
                    onAceptarOferta = {
                        showOfferReceivedDialog = false
                        showAgreementReachedDialog = true
                    },
                    onContraoferta = {
                        showOfferReceivedDialog = false
                        navController.navigate(Screen.CounterOffer.route)
                    },
                    onRechazarOferta = {
                        showOfferReceivedDialog = false
                    }
                )
            }

            if (showAgreementReachedDialog) {
                AgreementReachedDialog(
                    onDismiss = { showAgreementReachedDialog = false },
                    onReadyClicked = { showAgreementReachedDialog = false },
                    onViewDetailsClicked = { showAgreementReachedDialog = false }
                )
            }
        }
    }
}