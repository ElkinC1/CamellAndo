package com.example.camellando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.camellando.ui.navigation.AppNavigation
import com.example.camellando.ui.feed.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Invocamos directamente tu pantalla de registro para probarla
                HomeScreen()
            }
        }
    }
}