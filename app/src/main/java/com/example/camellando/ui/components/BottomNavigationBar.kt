package com.example.camellando.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.camellando.ui.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route

    BottomNavigationBar(
        currentRoute = currentRoute,
        onNavigate = { screen ->
            if (screen == Screen.Home) {
                if (currentRoute != Screen.Home.route) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            } else {
                if (currentRoute != screen.route) {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Home.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun BottomNavigationBar(
    currentRoute: String,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryBlue = Color(0xFF1E60F9)

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp,
        modifier = modifier
    ) {
        // 1. Inicio
        NavigationBarItem(
            selected = currentRoute == Screen.Home.route,
            onClick = { onNavigate(Screen.Home) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue
            )
        )

        // 2. Mensajes / Chat
        NavigationBarItem(
            selected = currentRoute == Screen.Chat.route,
            onClick = { onNavigate(Screen.Chat) },
            icon = { Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Mensajes") },
            label = { Text("Mensajes", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue
            )
        )

        // 3. Buscar
        NavigationBarItem(
            selected = currentRoute == Screen.Search.route,
            onClick = { onNavigate(Screen.Search) },
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            label = { Text("Buscar", fontSize = 11.sp) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = primaryBlue,
                selectedTextColor = primaryBlue
            )
        )
    }
}