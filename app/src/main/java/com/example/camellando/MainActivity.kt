package com.example.camellando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.material3.MaterialTheme
import com.example.camellando.ui.navigation.AppNavigation
import com.example.camellando.data.di.AppContainer
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.auth.handleDeeplinks

@AndroidEntryPoint(ComponentActivity::class)
class MainActivity : Hilt_MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        AppContainer.supabase.handleDeeplinks(intent)
        setContent {
            MaterialTheme {
                AppNavigation()
            }
        }
    }
}
