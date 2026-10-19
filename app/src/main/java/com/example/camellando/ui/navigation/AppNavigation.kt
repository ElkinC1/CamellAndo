<<<<<<< HEAD

package com.example.camellando.ui.navigation
=======
<<<<<<< HEAD

=======
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
package com.example.camellando.ui.navigation

>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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

<<<<<<< HEAD

=======
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navController,
<<<<<<< HEAD
        startDestination = Screen.Login.route
=======
        startDestination = Screen.CounterOfferReceived.route/*Screen.Login.route*/
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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
<<<<<<< HEAD
                        popUpTo(Screen.Login.route) {
                            inclusive = false
                        }
=======
                        popUpTo(Screen.Login.route) { inclusive = false }
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                        launchSingleTop = true
                    }
                }
            )
        }

<<<<<<< HEAD
=======
<<<<<<< HEAD
        // Crear una oferta
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
        composable(Screen.CreateOffer.route) {
            CreateOfferDialog(
                onDismiss = {
                    navController.popBackStack()
                },
                onOfertar = {
                    navController.navigate(Screen.OfferReceived.route)
                }
            )
<<<<<<< HEAD
=======
=======
        composable(Screen.CreateOffer.route) {
            CreateOfferDialog()
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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
=======
<<<<<<< HEAD
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                val popped = navController.popBackStack(
                    Screen.Home.route,
                    inclusive = false
                )

                if (!popped) {
                    navController.navigate(Screen.Home.route) {
<<<<<<< HEAD
=======
=======
                val popped = navController.popBackStack(Screen.Home.route, inclusive = false)
                if (!popped) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = false }
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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
            ProviderDetailScreen()
        }

=======
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
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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

<<<<<<< HEAD
=======
        // Contraoferta
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
        composable(Screen.CounterOffer.route) {
            CounterOfferDialog(
                onDismiss = {
                    navController.popBackStack()
                }
            )
<<<<<<< HEAD
=======
=======
            ProviderDetailScreen()
        }

        composable(Screen.OfferReceived.route) {
            OfferReceivedDialog()
        }

        composable(Screen.CounterOffer.route) {
            CounterOfferDialog()
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
        }

        composable(Screen.CounterOfferReceived.route) {
            CounterOfferReceivedDialog(
<<<<<<< HEAD
                onDismiss = {
                    navController.popBackStack()
                },
=======
                onDismiss = { navController.popBackStack() },
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
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
=======
<<<<<<< HEAD
        // Acuerdo alcanzado al aceptar la oferta
=======
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
        composable(Screen.AgreementReached.route) {
            AgreementReachedDialog()
        }
    }
<<<<<<< HEAD
}
=======
<<<<<<< HEAD
}

=======
}
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
