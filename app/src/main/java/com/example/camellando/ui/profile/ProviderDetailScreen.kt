<<<<<<< HEAD
=======
<<<<<<< HEAD

package com.example.camellando.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
=======
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
package com.example.camellando.ui.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
<<<<<<< HEAD
=======
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
<<<<<<< HEAD
=======
<<<<<<< HEAD
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815

@Composable
fun ProviderDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onContactarYContratar: () -> Unit = {}
) {
<<<<<<< HEAD
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Interfaz 9: Perfil Profesional (ProviderDetailScreen)")
    }
}
=======
    val azul = Color(0xFF2563EB)
    val textoOscuro = Color(0xFF0F172A)
    val textoSecundario = Color(0xFF64748B)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar",
                    tint = textoOscuro
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Perfil profesional",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textoOscuro
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    modifier = Modifier.size(100.dp),
                    shape = CircleShape,
                    color = Color(0xFFDBEAFE)
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "JA",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = azul
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Juan Álvarez",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Perfil verificado",
                        tint = azul,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "Electricista Matriculado",
                    fontSize = 15.sp,
                    color = azul
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = Color(0xFFECFDF5),
                    shape = RoundedCornerShape(50.dp)
                ) {
                    Text(
                        text = "● Activo",
                        modifier = Modifier.padding(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        ),
                        fontSize = 13.sp,
                        color = Color(0xFF059669),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = Color(0xFFFBBF24),
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "4.9",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = textoOscuro
                    )

                    Text(
                        text = " (48 reseñas)",
                        fontSize = 12.sp,
                        color = textoSecundario
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Información del profesional",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = textoOscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Especialidad",
                    fontWeight = FontWeight.SemiBold,
                    color = textoOscuro
                )

                Text(
                    text = "Instalaciones eléctricas, cableado, " +
                            "mantenimiento y reparación de tableros.",
                    color = textoSecundario,
                    fontSize = 14.sp
                )

                Text(
                    text = "Experiencia",
                    fontWeight = FontWeight.SemiBold,
                    color = textoOscuro
                )

                Text(
                    text = "8 años de experiencia",
                    color = textoSecundario,
                    fontSize = 14.sp
                )

                Text(
                    text = "Rango de precios",
                    fontWeight = FontWeight.SemiBold,
                    color = textoOscuro
                )

                Text(
                    text = "$25 - $45 por hora",
                    color = textoSecundario,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onContactarYContratar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = azul
            )
        ) {
            Icon(
                imageVector = Icons.Default.Chat,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Contactar y Contratar",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Podrás revisar la oferta antes de continuar.",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            color = textoSecundario
        )
=======

@Composable
fun ProviderDetailScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Interfaz 9: Perfil Profesional (ProviderDetailScreen)")
>>>>>>> 71c63baa8b6560d433643d67fae55dd209852bd6
    }
}
>>>>>>> 200c4fc1ba5e96803eda665dc9ac620fd96cd815
