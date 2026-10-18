package com.example.camellando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.camellando.ui.navigation.AppNavigation
import com.example.camellando.ui.theme.CamellAndoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CamellAndoTheme {
                AppNavigation()
            }
        }
    }
}