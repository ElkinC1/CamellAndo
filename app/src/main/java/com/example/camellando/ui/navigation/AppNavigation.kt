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
    val navigateToHome: () -> Unit = {
        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Home.route) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

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
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onRegisterClientSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onRegisterProviderSuccess = {
                    navController.navigate(Screen.ServiceProfileSetup.route)
                }
            )
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
                        popUpTo(Screen.Login.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            )
        }


        // Crear una oferta
        composable(Screen.CreateOffer.route) {
            CreateOfferDialog(
                onDismiss = navigateToHome,
                onOfertar = {
                    navigateToHome()
                }
            )
        }

        composable(Screen.ProviderProfile.route) {
            ProviderProfileScreen(onNavigateToPortfolio = {navController.navigate(Screen.ProviderDetail.route) })
        }

        composable(Screen.Home.route) {
            HomeScreen(
                viewModel = homeViewModel,
                navController = navController,
                onNavigateToCreatePost = {
                    navController.navigate(Screen.CreatePost.route)
                },
                onNavigateToDetail = { postId ->
                    navController.navigate(Screen.CreateOffer.route) {
                        launchSingleTop = true
                    }
                },
                onNavigateToSearch = {
                    navController.navigate(Screen.Search.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.ClientProfile.route)
                },
                onNavigateToProviderProfile = {
                    navController.navigate(Screen.ProviderProfile.route)
                },
                onNavigateToChat = {
                    navController.navigate(Screen.Chat.route)
                },
                onNavigateToOfferReceived = {
                    navController.navigate(Screen.OfferReceived.route)
                }
            )
        }

        composable(Screen.CreatePost.route) {
            val navigateToHome = {
                val popped = navController.popBackStack(
                    Screen.Home.route,
                    inclusive = false
                )

                if (!popped) {
                    navController.navigate(Screen.Home.route) {
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
                navController = navController,
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
            ProviderDetailScreen(onBack = {navController.popBackStack()})
        }


        // Oferta recibida
        composable(Screen.OfferReceived.route) {
            OfferReceivedDialog(
                onDismiss = navigateToHome,
                onAceptarOferta = {
                    navController.navigate(Screen.AgreementReached.route)
                },
                onContraoferta = {
                    navController.navigate(Screen.CounterOffer.route)
                },
                onRechazarOferta = navigateToHome
            )
        }

        // Contraoferta
        composable(Screen.CounterOffer.route) {
            CounterOfferReceivedDialog(
                onDismiss = navigateToHome,
                onAceptarContraoferta = {
                    navController.navigate(Screen.AgreementReached.route)
                },
                onContraoferta = {
                    navController.navigate(Screen.CreateOffer.route)
                },
                onRechazarContraoferta = navigateToHome
            )
        }

        composable(Screen.CounterOfferReceived.route) {
            CounterOfferReceivedDialog(
                onDismiss = navigateToHome,
                onAceptarContraoferta = {
                    navController.navigate(Screen.AgreementReached.route)
                },
                onContraoferta = {
                    navController.navigate(Screen.CreateOffer.route)
                },
                onRechazarContraoferta = navigateToHome
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
            ChatScreen(
                navController = navController,
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        // Acuerdo alcanzado al aceptar la oferta
        composable(Screen.AgreementReached.route) {
            val navigateToHome = {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = false
                    }
                    launchSingleTop = true
                }
            }

            AgreementReachedDialog(
                onDismiss = navigateToHome,
                onReadyClicked = navigateToHome
            )
        }
    }
}