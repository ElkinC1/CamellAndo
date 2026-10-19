<<<<<<< HEAD

=======
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
package com.example.camellando.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
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
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screen.CounterOfferReceived.route/*Screen.Login.route*/
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
            ServiceProfileSetupScreen(
                onBackClick = {
                    if (!navController.popBackStack()) {
                        navController.navigate(Screen.Register.route)
                    }
                },
                onRegistrarClick = {
                    navController.navigate(Screen.Login.route) {
<<<<<<< HEAD
                        popUpTo(Screen.Login.route) {
                            inclusive = false
                        }
=======
                        popUpTo(Screen.Login.route) { inclusive = false }
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
                        launchSingleTop = true
                    }
                }
            )
        }

<<<<<<< HEAD
        // Crear una oferta
        composable(Screen.CreateOffer.route) {
            CreateOfferDialog(
                onDismiss = {
                    navController.popBackStack()
                },
                onOfertar = {
                    navController.navigate(Screen.OfferReceived.route)
                }
            )
=======
        composable(Screen.CreateOffer.route) {
            CreateOfferDialog()
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
        }

        composable(Screen.ProviderProfile.route) {
            ProviderProfileScreen()
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
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
            val navigateToHome = {
<<<<<<< HEAD
                val popped = navController.popBackStack(
                    Screen.Home.route,
                    inclusive = false
                )

                if (!popped) {
                    navController.navigate(Screen.Home.route) {
=======
                val popped = navController.popBackStack(Screen.Home.route, inclusive = false)
                if (!popped) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
                        launchSingleTop = true
                    }
                }
            }

            CreatePostScreen(
                onCloseClick = {
                    navigateToHome()
                },
                onPublicarClick = { texto, fotos ->
                    homeViewModel.agregarPublicacion(texto, fotos)
                    navigateToHome()
                }
            )
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
<<<<<<< HEAD
            ProviderDetailScreen(
                onBack = {
                    navController.popBackStack()
                },
                onContactarYContratar = {
                    navController.navigate(Screen.CreateOffer.route)
                }
            )
        }


        // Oferta recibida
        composable(Screen.OfferReceived.route) {
            OfferReceivedDialog(
                onDismiss = {
                    navController.popBackStack()
                },
                onAceptarOferta = {
                    navController.navigate(Screen.AgreementReached.route)
                },
                onContraoferta = {
                    navController.navigate(Screen.CounterOffer.route)
                },
                onRechazarOferta = {
                    navController.popBackStack()
                }
            )
        }

        // Contraoferta
        composable(Screen.CounterOffer.route) {
            CounterOfferDialog(
                onDismiss = {
                    navController.popBackStack()
                }
            )
=======
            ProviderDetailScreen()
        }

        composable(Screen.OfferReceived.route) {
            OfferReceivedDialog()
        }

        composable(Screen.CounterOffer.route) {
            CounterOfferDialog()
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
        }

        composable(Screen.CounterOfferReceived.route) {
            CounterOfferReceivedDialog(
                onDismiss = { navController.popBackStack() },
                onAceptarContraoferta = {
                    navController.navigate(Screen.AgreementReached.route)
                },
                onContraoferta = {
                    navController.navigate(Screen.CreateOffer.route)
                },
                onRechazarContraoferta = {
                    navController.navigate(Screen.OfferReceived.route)
                }
            )
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

<<<<<<< HEAD
        // Acuerdo alcanzado al aceptar la oferta
=======
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
        composable(Screen.AgreementReached.route) {
            AgreementReachedDialog()
        }
    }
<<<<<<< HEAD
}

=======
}
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
