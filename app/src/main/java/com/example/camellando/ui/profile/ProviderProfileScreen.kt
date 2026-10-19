package com.example.camellando.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.camellando.data.mock.ProviderDummy
import com.example.camellando.data.mock.mockProviders
import androidx.compose.foundation.Image
import androidx.compose.ui.res.painterResource
import com.example.camellando.R
import com.example.camellando.data.mock.mockReviews
import androidx.compose.ui.layout.ContentScale
import com.example.camellando.data.mock.PostDummy
import com.example.camellando.data.mock.mockPosts

private val ProfileBlue = Color(0xFF2563EB)
private val ProfileText = Color(0xFF111827)
private val ProfileGray = Color(0xFF6B7280)
private val ProfileGold = Color(0xFFF59E0B)

@Composable
fun ProviderProfileScreen(
    modifier: Modifier = Modifier
) {
    val provider = mockProviders.first()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF3F4F6))
            .verticalScroll(rememberScrollState())
    ) {
        ProviderHeader(provider)
        Spacer(modifier = Modifier.height(12.dp))
        PortfolioSummary()
        Spacer(Modifier.height(12.dp))
        RecentReviews()
        Spacer(Modifier.height(12.dp))
        ProviderPosts()
    }
}
@Composable
private fun ProviderHeader(provider: ProviderDummy) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Portada: por ahora usamos un fondo gris.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(125.dp)
                .background(Color(0xFFD9D9D9))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 69.dp)
                .padding(horizontal = 20.dp)
        ) {
            // Avatar provisional con las iniciales.
            Box(
                modifier = Modifier
                    .size(112.dp)
                    .border(
                        width = 2.dp,
                        color = Color(0xFF087E99),
                        shape = CircleShape
                    )
                    .background(Color.White, CircleShape)
                    .padding(4.dp)
                    .background(
                        color = Color(0xFFE8F0F8),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = provider.name
                        .split(" ")
                        .take(2)
                        .mapNotNull { it.firstOrNull() }
                        .joinToString(""),
                    color = ProfileBlue,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Nombre del prestador.
            Text(
                text = provider.name,
                fontSize = 25.sp,
                lineHeight = 30.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Oficio.
            Text(
                text = provider.profession,
                fontSize = 17.sp,
                color = ProfileText
            )

            // Rango de precios.
            Text(
                text = provider.priceRange,
                fontSize = 17.sp,
                color = ProfileText
            )

            // Ciudad o zona de cobertura.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.LocationOn,
                    contentDescription = "Ciudad",
                    tint = ProfileGray,
                    modifier = Modifier.size(18.dp)
                )

                Text(
                    text = provider.city,
                    fontSize = 16.sp,
                    color = ProfileText
                )
            }

            // Estado del prestador.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .background(
                            color = if (provider.isActive) {
                                Color(0xFF16A34A)
                            } else {
                                Color(0xFF9CA3AF)
                            },
                            shape = CircleShape
                        )
                )

                Text(
                    text = if (provider.isActive) "Activo" else "Inactivo",
                    fontSize = 16.sp,
                    color = if (provider.isActive) {
                        Color(0xFF15803D)
                    } else {
                        ProfileGray
                    }
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            // Calificación y número de reseñas.
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = "Calificación",
                    tint = ProfileGold,
                    modifier = Modifier.size(23.dp)
                )

                Text(
                    text = provider.rating.toString(),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ProfileText
                )

                Text(
                    text = "(${provider.reviewCount} reseñas)",
                    fontSize = 15.sp,
                    color = ProfileGray
                )
            }
        }
    }
}


@Composable
private fun PortfolioSummary(
    onViewPortfolio: () -> Unit = {}
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Portafolio",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = ProfileText
                )

                androidx.compose.material3.TextButton(
                    onClick = onViewPortfolio
                ) {
                    Text(
                        text = "Ver portafolio",
                        color = ProfileBlue,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(
                        androidx.compose.foundation.rememberScrollState()
                    )
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                PortfolioImage(
                    imageRes = R.drawable.portfolio_electrical,
                    description = "Instalación eléctrica"
                )

                PortfolioImage(
                    imageRes = R.drawable.portfolio_lighting,
                    description = "Iluminación residencial"
                )

                PortfolioImage(
                    imageRes = R.drawable.portfolio_wiring,
                    description = "Cableado eléctrico"
                )
            }
        }
    }
}

@Composable
private fun PortfolioImage(
    imageRes: Int,
    description: String
) {
    Column(
        modifier = Modifier.width(155.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = description,
            modifier = Modifier
                .fillMaxWidth()
                .height(105.dp)
                .background(
                    Color(0xFFE5E7EB),
                    RoundedCornerShape(12.dp)
                ),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = description,
            fontSize = 13.sp,
            color = ProfileText,
            fontWeight = FontWeight.Medium
        )
    }
}


@Composable
private fun RecentReviews(
    onViewAll: () -> Unit = {}
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            // Título
            Text(
                text = "Reseñas recientes",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = ProfileText
            )

            Spacer(Modifier.height(8.dp))

            // Lista de reseñas
            mockReviews.forEachIndexed { index, review ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = review.username,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ProfileText
                    )

                    Spacer(Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(review.rating) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = null,
                                tint = ProfileGold,
                                modifier = Modifier.size(17.dp)
                            )
                        }

                        Spacer(Modifier.width(6.dp))

                        Text(
                            text = "${review.rating}/5",
                            fontSize = 13.sp,
                            color = ProfileGray
                        )
                    }

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = review.description,
                        fontSize = 14.sp,
                        color = ProfileText,
                        lineHeight = 20.sp
                    )
                }

                if (index < mockReviews.lastIndex) {
                    androidx.compose.material3.HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = Color(0xFFE5E7EB)
                    )
                }
            }

            // Enlace inferior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.TextButton(
                    onClick = onViewAll
                ) {
                    Text(
                        text = "Ver todas las reseñas",
                        color = ProfileBlue,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}
@Composable
private fun ProviderPosts() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = "Publicaciones",
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold,
            color = ProfileText,
            modifier = Modifier.padding(
                horizontal = 20.dp,
                vertical = 16.dp
            )
        )

        mockPosts.forEach { post ->
            ProviderPostCard(post = post)
            Spacer(Modifier.height(12.dp))
        }
    }
}


@Composable
private fun ProviderPostCard(post: PostDummy) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = androidx.compose.material3.CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp)
        ) {
            // Nombre del prestador y fecha
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            Color(0xFFE8F0F8),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = post.providerName
                            .split(" ")
                            .take(2)
                            .mapNotNull { it.firstOrNull() }
                            .joinToString(""),
                        color = ProfileBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }

                Spacer(Modifier.width(10.dp))

                Column {
                    Text(
                        text = post.providerName,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = ProfileText
                    )

                    Spacer(Modifier.height(2.dp))

                    Text(
                        text = post.date,
                        fontSize = 12.sp,
                        color = ProfileGray
                    )
                }
            }

            // Descripción de la publicación
            Spacer(Modifier.height(12.dp))

            Text(
                text = post.description,
                modifier = Modifier.padding(horizontal = 14.dp),
                fontSize = 14.sp,
                lineHeight = 21.sp,
                color = ProfileText
            )

            // Galería de imágenes
            if (post.imageRes.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState())
                        .padding(horizontal = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    post.imageRes.forEachIndexed { index, imageRes ->
                        Image(
                            painter = painterResource(imageRes),
                            contentDescription =
                                "Imagen ${index + 1} de la publicación",
                            modifier = Modifier
                                .width(230.dp)
                                .height(170.dp)
                                .background(
                                    Color(0xFFE5E7EB),
                                    RoundedCornerShape(10.dp)
                                )
                                .then(
                                    Modifier
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

