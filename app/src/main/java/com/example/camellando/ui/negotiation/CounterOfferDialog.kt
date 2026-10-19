package com.example.camellando.ui.negotiation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CounterOfferDialog(onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Interfaz 11: Modal Contraoferta de Servicio") },
        text = { Text("Modal para proponer nuevo precio o condiciones.") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Enviar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
