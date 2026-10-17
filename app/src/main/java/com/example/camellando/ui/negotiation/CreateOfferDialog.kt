package com.example.camellando.ui.negotiation

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun CreateOfferDialog(onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Interfaz 4: Modal Ofertar") },
        text = { Text("Modal para definir tarea y presupuesto.") },
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("Enviar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
