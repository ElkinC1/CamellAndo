package com.example.camellando.ui.negotiation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.camellando.R

data class AcuerdoDetalleModel(
    val id: String = "a101",
    val tituloServicio: String = "Reparación de toma eléctrico y cableado",
    val montoAcordado: String = "$45.00",
    val moneda: String = "USD",
    @DrawableRes val imagenIlustracionResId: Int? = R.drawable.acuerdoalcanzado
)

@Composable
fun AgreementReachedDialog(
    acuerdo: AcuerdoDetalleModel = AcuerdoDetalleModel(),
    onDismiss: () -> Unit = {},
    onReadyClicked: () -> Unit = {},
    onViewDetailsClicked: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        AgreementReachedContent(
            acuerdo = acuerdo,
            onDismiss = onDismiss,
            onReadyClicked = onReadyClicked,
            onViewDetailsClicked = onViewDetailsClicked
        )
    }
}

@Composable
private fun AgreementReachedContent(
    acuerdo: AcuerdoDetalleModel,
    onDismiss: () -> Unit,
    onReadyClicked: () -> Unit,
    onViewDetailsClicked: () -> Unit
) {
    val primaryBlue = Color(0xFF1E60F9)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .padding(horizontal = 20.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón Cierre "X" en la esquina superior derecha
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFF1F5F9), CircleShape)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Cerrar",
                            tint = Color(0xFF334155),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                // Ilustración de Apretón de Manos / Trato Cerrado con Badge Verde
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Surface(
                        modifier = Modifier.size(90.dp),
                        shape = CircleShape,
                        color = Color(0xFFF0F7FF)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            if (acuerdo.imagenIlustracionResId != null) {
                                Image(
                                    painter = painterResource(id = acuerdo.imagenIlustracionResId),
                                    contentDescription = "Trato cerrado",
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Icon(
                                    Icons.Default.Handshake,
                                    contentDescription = null,
                                    tint = primaryBlue,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }
                    }

                    // Badge Verde de Check "✓"
                    Box(
                        modifier = Modifier
                            .size(26.dp)
                            .background(Color(0xFF10B981), CircleShape)
                            .align(Alignment.BottomEnd)
                            .padding(2.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Encabezado y Mensaje Principal
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Acuerdo alcanzado",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "¡Trato cerrado! Ambas partes han validado los términos con éxito.",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B),
                        textAlign = TextAlign.Center,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }

                // Tarjeta Resumen: Servicio Fijado + Monto
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "SERVICIO FIJADO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )

                        Text(
                            text = acuerdo.tituloServicio,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A),
                            lineHeight = 18.sp
                        )

                        HorizontalDivider(color = Color(0xFFE2E8F0), thickness = 1.dp)

                        Column {
                            Text(
                                text = "Monto acordado",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = acuerdo.montoAcordado,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF0F172A)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = acuerdo.moneda,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF94A3B8),
                                    modifier = Modifier.padding(bottom = 3.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                // Botón Principal: "Listo ✓"
                Button(
                    onClick = onReadyClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Listo",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Enlace Secundario: "Ver detalles de acuerdo"
                TextButton(
                    onClick = onViewDetailsClicked,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Ver detalles de acuerdo",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64748B)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AgreementReachedDialogPreview() {
    MaterialTheme {
        AgreementReachedContent(
            acuerdo = AcuerdoDetalleModel(),
            onDismiss = {},
            onReadyClicked = {},
            onViewDetailsClicked = {}
        )
    }
}