package com.example.camellando.ui.negotiation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun OfferReceivedDialog(onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("10. Oferta Recibida") },
        text = { Text("Modal para aceptar o contraofertar.") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Aceptar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Contraoferta") }
        }
    )
}
