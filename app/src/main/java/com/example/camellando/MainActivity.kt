package com.example.camellando

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.example.camellando.ui.navigation.AppNavigation
import com.example.camellando.ui.feed.HomeScreen
import com.example.camellando.ui.negotiation.AgreementReachedDialog
import com.example.camellando.ui.negotiation.OfferReceivedDialog

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AgreementReachedDialog ()
            }
        }
    }
}