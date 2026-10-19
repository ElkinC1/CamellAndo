package com.example.camellando.ui.negotiation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.SyncAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.camellando.R

data class ClienteOfertaModel(
    val id: String = "c1",
    val nombre: String = "Carlos Mendoza", // Usuario creado en las tarjetas del feed
    val rol: String = "CLIENTE",
    val calificacion: Double = 4.9,
    val totalResenas: Int = 24,
    val ubicacion: String = "Zona Norte • A 2.4 km de ti",
    val estaEnLinea: Boolean = true,
    @get:DrawableRes val fotoPerfilResId: Int? = null
)

data class OfertaDetalleModel(
    val id: String = "o101",
    val tiempoTranscurrido: String = "Hace 3 min",
    val tituloServicio: String = "Instalación eléctrica y tablero bifásico",
    val descripcionServicio: String = "Requiere revisión del panel principal, cableado de 4 tomas de corriente e instalación de disyuntor.",
    val fechaHora: String = "12/08/2026, 09:00 AM",
    val categoria: String = "Electricidad",
    val presupuestoOfrecido: String = "$65.00",
    val moneda: String = "USD",
    val cliente: ClienteOfertaModel = ClienteOfertaModel()
)

@Composable
fun OfferReceivedDialog(
    oferta: OfertaDetalleModel = OfertaDetalleModel(),
    onDismiss: () -> Unit = {},
    onAceptarOferta: () -> Unit = {},
    onContraoferta: () -> Unit = {},
    onRechazarOferta: () -> Unit = {}
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        OfferReceivedContent(
            oferta = oferta,
            onDismiss = onDismiss,
            onAceptarOferta = onAceptarOferta,
            onContraoferta = onContraoferta,
            onRechazarOferta = onRechazarOferta
        )
    }
}

@Composable
private fun OfferReceivedContent(
    oferta: OfertaDetalleModel,
    onDismiss: () -> Unit,
    onAceptarOferta: () -> Unit,
    onContraoferta: () -> Unit,
    onRechazarOferta: () -> Unit
) {
    val primaryBlue = Color(0xFF1E60F9)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.45f))
            .padding(horizontal = 16.dp, vertical = 24.dp),
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
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón Cierre "X"
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

                // Encabezado
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            color = Color(0xFFEFF6FF),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "• Nueva Solicitud Directa",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryBlue,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                            )
                        }
                        Text(
                            text = oferta.tiempoTranscurrido,
                            fontSize = 12.sp,
                            color = Color(0xFF94A3B8)
                        )
                    }

                    Text(
                        text = "Oferta Recibida",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0F172A)
                    )
                    Text(
                        text = "Un cliente ha solicitado tus servicios en CamellAndo.",
                        fontSize = 13.sp,
                        color = Color(0xFF64748B)
                    )
                }

                // 1. Datos del Cliente (Carlos Mendoza)
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box {
                            val isPreview = LocalInspectionMode.current
                            val context = LocalContext.current
                            val resolvedDrawableId = remember(oferta.cliente.fotoPerfilResId) {
                                oferta.cliente.fotoPerfilResId ?: runCatching {
                                    context.resources.getIdentifier("electricista", "drawable", context.packageName).takeIf { it != 0 }
                                }.getOrNull()
                            }

                            if (resolvedDrawableId != null && !isPreview) {
                                Image(
                                    painter = painterResource(id = resolvedDrawableId),
                                    contentDescription = "Foto de perfil de ${oferta.cliente.nombre}",
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = CircleShape,
                                    color = Color(0xFFEFF6FF)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = oferta.cliente.nombre
                                                .split(" ")
                                                .take(2)
                                                .mapNotNull { it.firstOrNull() }
                                                .joinToString(""),
                                            color = primaryBlue,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }

                            // Punto verde de en línea
                            if (oferta.cliente.estaEnLinea) {
                                Box(
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(Color(0xFF10B981), CircleShape)
                                        .align(Alignment.BottomEnd)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = oferta.cliente.rol,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8)
                            )
                            Text(
                                text = oferta.cliente.nombre,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = oferta.cliente.ubicacion,
                                fontSize = 11.sp,
                                color = Color(0xFF64748B),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        Surface(
                            color = Color(0xFFFEF3C7),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Star,
                                    contentDescription = null,
                                    tint = Color(0xFFD97706),
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = "${oferta.cliente.calificacion} (${oferta.cliente.totalResenas})",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                            }
                        }
                    }
                }

                // 2. Servicio Solicitado
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "SERVICIO SOLICITADO",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF94A3B8)
                        )

                        Row(verticalAlignment = Alignment.Top) {
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFEFF6FF),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Default.FlashOn,
                                        contentDescription = null,
                                        tint = primaryBlue,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Column {
                                Text(
                                    text = oferta.tituloServicio,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F172A)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = oferta.descripcionServicio,
                                    fontSize = 12.sp,
                                    color = Color(0xFF64748B),
                                    lineHeight = 16.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = Color(0xFF64748B),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = oferta.fechaHora,
                                        fontSize = 11.sp,
                                        color = Color(0xFF475569)
                                    )
                                }
                            }

                            Surface(
                                color = Color.White,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.FlashOn,
                                        contentDescription = null,
                                        tint = Color(0xFFD97706),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = oferta.categoria,
                                        fontSize = 11.sp,
                                        color = Color(0xFF475569)
                                    )
                                }
                            }
                        }
                    }
                }

                // 3. Presupuesto Ofrecido
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF0F7FF)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "PRESUPUESTO OFRECIDO",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryBlue
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Monto total fijado por el cliente",
                                fontSize = 11.sp,
                                color = Color(0xFF64748B)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = oferta.presupuestoOfrecido,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF0F172A)
                            )
                            Text(
                                text = oferta.moneda,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF94A3B8)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Botones de Acción
                Button(
                    onClick = onAceptarOferta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.Check,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Aceptar Oferta",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                OutlinedButton(
                    onClick = onContraoferta,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.SyncAlt,
                            contentDescription = null,
                            tint = primaryBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Contraoferta",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryBlue
                        )
                    }
                }

                TextButton(
                    onClick = onRechazarOferta,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "✕ Rechazar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEF4444)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 412, heightDp = 892)
@Composable
private fun OfferReceivedDialogPreview() {
    MaterialTheme {
        OfferReceivedContent(
            oferta = OfertaDetalleModel(),
            onDismiss = {},
            onAceptarOferta = {},
            onContraoferta = {},
            onRechazarOferta = {}
        )
    }
}