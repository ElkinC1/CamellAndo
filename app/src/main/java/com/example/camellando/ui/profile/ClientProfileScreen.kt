package com.example.camellando.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ClientBlue = Color(0xFF2563EB)
private val ClientGray = Color(0xFF64748B)

@Composable
fun ClientProfileScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onSettings: () -> Unit = {},
    onViewDetail: () -> Unit = {}
) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFF7F9FC))
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    contentDescription = "Volver",
                    tint = Color(0xFF334155)
                )
            }

            IconButton(onClick = onSettings) {
                Icon(
                    Icons.Outlined.Settings,
                    contentDescription = "Configuración",
                    tint = Color(0xFF334155)
                )
            }
        }

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier
                    .padding(top = 3.dp)
                    .size(104.dp)
                    .background(
                        brush = androidx.compose.ui.graphics.Brush.linearGradient(
                            listOf(
                                Color(0xFF3B82F6),
                                Color(0xFF1D4ED8)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "U",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .size(25.dp)
                        .background(Color(0xFF10B981), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Outlined.Check,
                        contentDescription = "Cuenta activa",
                        tint = Color.White,
                        modifier = Modifier.size(17.dp)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Text(
                "Usuario Contratador",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Text(
                "Cliente activo desde Feb 2024 • Manta",
                fontSize = 12.sp,
                color = ClientGray
            )

            Spacer(Modifier.height(16.dp))

            Surface(
                color = Color.White,
                shape = RoundedCornerShape(18.dp),
                shadowElevation = 1.dp
            ) {
                Column(
                    Modifier.padding(
                        horizontal = 22.dp,
                        vertical = 10.dp
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "★★★★◐☆",
                        color = Color(0xFFF4B51C),
                        fontSize = 24.sp
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "3.5",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )

                        Spacer(Modifier.width(6.dp))

                        Text(
                            "/ 5.0 • 14 evaluaciones",
                            fontSize = 12.sp,
                            color = ClientGray
                        )
                    }
                }
            }

            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 28.dp,
                        bottom = 12.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier
                        .width(5.dp)
                        .height(18.dp)
                        .background(
                            ClientBlue,
                            RoundedCornerShape(4.dp)
                        )
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    "Historial de Contrataciones",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F2937)
                )

                Spacer(Modifier.weight(1f))

                Surface(
                    color = Color(0xFFEEF4FF),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        "3 Servicios",
                        modifier = Modifier.padding(
                            horizontal = 10.dp,
                            vertical = 5.dp
                        ),
                        fontSize = 11.sp,
                        color = ClientBlue
                    )
                }
            }

            Surface(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                color = Color.White,
                shape = RoundedCornerShape(20.dp),
                shadowElevation = 1.dp
            ) {
                Column(Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            Modifier
                                .size(42.dp)
                                .background(
                                    Color(0xFFF1F5F9),
                                    CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "JA",
                                color = ClientBlue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        Column(Modifier.weight(1f)) {
                            Text(
                                "PRESTADOR DE SERVICIO",
                                fontSize = 11.sp,
                                color = ClientGray,
                                letterSpacing = 1.sp
                            )

                            Text(
                                "Juan Álvarez",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1F2937)
                            )

                            Text(
                                "Electricista matriculado",
                                fontSize = 11.sp,
                                color = ClientGray
                            )
                        }

                        Surface(
                            color = Color(0xFFE9FBF3),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                "Finalizado",
                                modifier = Modifier.padding(
                                    horizontal = 10.dp,
                                    vertical = 8.dp
                                ),
                                color = Color(0xFF16805D),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Divider(
                        Modifier.padding(vertical = 16.dp),
                        color = Color(0xFFF1F5F9)
                    )

                    Text(
                        "SERVICIO REALIZADO",
                        fontSize = 12.sp,
                        color = ClientGray,
                        letterSpacing = 1.sp
                    )

                    Spacer(Modifier.height(7.dp))

                    Text(
                        "Instalación de iluminación empotrada y cambio de térmicas en cuadro principal.",
                        fontSize = 12.sp,
                        lineHeight = 19.sp,
                        color = Color(0xFF1F2937)
                    )

                    Spacer(Modifier.height(12.dp))

                    Surface(
                        color = Color(0xFFF8FAFC),
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "CALIFICACIÓN OTORGADA:",
                                    fontSize = 12.sp,
                                    color = ClientGray,
                                    letterSpacing = .5.sp,
                                    modifier = Modifier.weight(1f)
                                )

                                Text(
                                    "★★★★★ 5.0",
                                    fontSize = 11.sp,
                                    color = Color(0xFFF4B51C),
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(Modifier.height(6.dp))

                            Text(
                                "\"Puntual, cumplió con el pago solicitado una vez terminé el servicio\"",
                                fontSize = 12.sp,
                                color = ClientGray,
                                lineHeight = 18.sp
                            )
                        }
                    }

                    Spacer(Modifier.height(18.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            "Fecha: 12 Mayo 2024",
                            fontSize = 12.sp,
                            color = ClientGray,
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = onViewDetail,
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Ver detalle ›",
                                color = ClientBlue,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }
    }
}