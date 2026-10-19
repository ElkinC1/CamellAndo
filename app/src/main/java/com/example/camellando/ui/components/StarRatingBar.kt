package com.example.camellando.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun StarRatingBar(
    rating: Float,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(text = "★ $rating")
    }
}
