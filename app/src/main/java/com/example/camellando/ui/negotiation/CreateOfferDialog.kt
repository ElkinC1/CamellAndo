
package com.example.camellando.ui.negotiation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.text.TextStyle
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
                            border = BorderStroke(
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
                            textStyle = TextStyle(
                                fontSize = 19.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

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
