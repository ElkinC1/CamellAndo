<<<<<<< HEAD

package com.example.camellando.ui.negotiation

import androidx.compose.foundation.BorderStroke
=======
<<<<<<< HEAD

package com.example.camellando.ui.negotiation

>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
<<<<<<< HEAD
import androidx.compose.ui.text.TextStyle
=======
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CreateOfferDialog(
    onDismiss: () -> Unit = {},
    onOfertar: () -> Unit = {}
) {
    var presupuesto by remember { mutableStateOf("45,00") }

    val azul = Color(0xFF2864E8)
    val texto = Color(0xFF273142)
    val fondoCampo = Color(0xFFF7F9FC)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF687386).copy(alpha = 0.38f))
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
<<<<<<< HEAD
=======
            // Botón X en la parte superior derecha
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
            IconButton(
                onClick = onDismiss,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 4.dp)
                    .size(44.dp)
                    .background(Color.White, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Cerrar",
                    tint = Color(0xFF475569),
                    modifier = Modifier.size(23.dp)
                )
            }

<<<<<<< HEAD
=======
            // Tarjeta central del modal
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(22.dp)
                ) {
<<<<<<< HEAD
=======
                    // Tarea
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Tarea",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = texto
                            )

                            Surface(
                                color = Color(0xFFEFF5FF),
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(
                                    text = "Oficio / Servicio",
                                    modifier = Modifier.padding(
                                        horizontal = 10.dp,
                                        vertical = 5.dp
                                    ),
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = azul
                                )
                            }
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            color = fondoCampo,
<<<<<<< HEAD
                            border = BorderStroke(
=======
                            border = androidx.compose.foundation.BorderStroke(
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                                1.dp,
                                Color(0xFFE8EDF4)
                            )
                        ) {
                            Text(
                                text = "Reparación de toma eléctrica y cableado",
                                modifier = Modifier.padding(
                                    horizontal = 14.dp,
                                    vertical = 17.dp
                                ),
                                fontSize = 14.sp,
                                color = texto,
                                lineHeight = 20.sp
                            )
                        }
                    }

<<<<<<< HEAD
=======
                    // Presupuesto
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Presupuesto",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = texto
                        )

                        OutlinedTextField(
                            value = presupuesto,
                            onValueChange = { nuevoValor ->
                                if (
                                    nuevoValor.all {
                                        it.isDigit() || it == ',' || it == '.'
                                    }
                                ) {
                                    presupuesto = nuevoValor
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            shape = RoundedCornerShape(14.dp),
                            leadingIcon = {
                                Text(
                                    text = "$",
                                    modifier = Modifier.padding(start = 4.dp),
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = texto
                                )
                            },
                            trailingIcon = {
                                Surface(
                                    color = Color(0xFFE9EEF5),
                                    shape = RoundedCornerShape(6.dp)
                                ) {
                                    Text(
                                        text = "USD",
                                        modifier = Modifier.padding(
                                            horizontal = 9.dp,
                                            vertical = 5.dp
                                        ),
                                        fontSize = 11.sp,
                                        color = Color(0xFF64748B)
                                    )
                                }
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = fondoCampo,
                                unfocusedContainerColor = fondoCampo,
                                focusedBorderColor = Color(0xFFE0E7F0),
                                unfocusedBorderColor = Color(0xFFE0E7F0),
                                focusedTextColor = texto,
                                unfocusedTextColor = texto
                            ),
<<<<<<< HEAD
                            textStyle = TextStyle(
=======
                            textStyle = androidx.compose.ui.text.TextStyle(
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

<<<<<<< HEAD
=======
                    // Botón Ofertar
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
                    Button(
                        onClick = onOfertar,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = azul
                        ),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 5.dp
                        )
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Ofertar",
                                fontSize = 17.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(21.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
<<<<<<< HEAD
=======

=======
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
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
