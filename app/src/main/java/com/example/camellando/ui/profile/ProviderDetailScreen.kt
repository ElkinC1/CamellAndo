package com.example.camellando.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.res.painterResource
import com.example.camellando.R
import androidx.compose.foundation.layout.statusBarsPadding

private val PortfolioBackground = Color(0xFFF3F4F6)
private val PortfolioBlue = Color(0xFF2563EB)
private val PortfolioText = Color(0xFF172B4D)
private val PortfolioMuted = Color(0xFF64748B)

@Composable
fun ProviderDetailScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PortfolioBackground)
    ) {
        PortfolioTopBar(onBack = onBack)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 20.dp)
        ) {
            Spacer(Modifier.height(12.dp))

            PortfolioProviderHeader()

            Spacer(Modifier.height(18.dp))

            PortfolioExperience()

            Spacer(Modifier.height(18.dp))

            PortfolioProjects()
        }
    }
}


@Composable
private fun PortfolioTopBar(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(PortfolioBackground)
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Regresar al perfil",
                tint = PortfolioMuted
            )
        }
        Spacer(Modifier.weight(1f))
        Text(
            text = "PORTAFOLIO PROFESIONAL",
            fontSize = 12.sp,
            color = PortfolioMuted,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.width(48.dp))
    }
}

@Composable
private fun PortfolioProviderHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(62.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(PortfolioBlue),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "J",
                fontSize = 27.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            Text(
                text = "Juan Álvarez",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PortfolioText
            )

            Text(
                text = "Electricista",
                fontSize = 12.sp,
                color = PortfolioBlue
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    tint = PortfolioMuted,
                    modifier = Modifier.size(13.dp)
                )

                Spacer(Modifier.width(2.dp))

                Text(
                    text = "San Mateo",
                    fontSize = 11.sp,
                    color = PortfolioMuted
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10B981))
                )

                Spacer(Modifier.width(5.dp))

                Text(
                    text = "Disponible",
                    fontSize = 11.sp,
                    color = Color(0xFF059669)
                )
            }
        }
    }
}

@Composable
private fun PortfolioExperience() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Conocimiento / Experiencia",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = PortfolioText
                )

                Surface(
                    color = Color(0xFFEFF6FF),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "8+ años exp.",
                        fontSize = 10.sp,
                        color = PortfolioBlue,
                        modifier = Modifier.padding(
                            horizontal = 7.dp,
                            vertical = 5.dp
                        )
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Especialista en instalaciones eléctricas residenciales y comerciales. Con sólida experiencia en diagnóstico preventivo, automatización de tableros eléctricos y cumplimiento estricto de normas de seguridad eléctrica y eficiencia energética.",
                fontSize = 12.sp,
                lineHeight = 18.sp,
                color = PortfolioMuted
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 12.dp),
                color = Color(0xFFF1F5F9)
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExperienceTag("Cuadros eléctricos")
                ExperienceTag("Cableado")
            }

            Spacer(Modifier.height(6.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ExperienceTag("Iluminación LED")
                ExperienceTag("Puesta a tierra")
                ExperienceTag("Normativa SEC")
            }
        }
    }
}

@Composable
private fun ExperienceTag(text: String) {
    Surface(
        color = Color(0xFFF8FAFC),
        shape = RoundedCornerShape(7.dp),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            Color(0xFFE2E8F0)
        )
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 8.dp,
                vertical = 6.dp
            ),
            fontSize = 10.sp,
            color = PortfolioText
        )
    }
}

@Composable
private fun PortfolioProjects() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = "Historial de Trabajos",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = PortfolioText
            )
        }

        Spacer(Modifier.height(10.dp))

        ProjectReviewCard(
            imageRes = R.drawable.portfolio_electrical,
            client = "Residencial Los Olivos",
            projectTitle = "Instalación de cuadro eléctrico",
            description = "Instalación completa de cuadro eléctrico general y recableado de potencia.",
            rating = "5.0",
            review = "Puntual, pulcro y excelente disposición."
        )

        Spacer(Modifier.height(12.dp))

        ProjectReviewCard(
            imageRes = R.drawable.portfolio_lighting,
            client = "Cliente residencial",
            projectTitle = "Iluminación residencial LED",
            description = "Instalación de luminarias LED y optimización de la iluminación interior.",
            rating = "4.9",
            review = "Muy buen trabajo y excelente acabado."
        )

        Spacer(Modifier.height(12.dp))

        ProjectReviewCard(
            imageRes = R.drawable.portfolio_wiring,
            client = "Proyecto residencial",
            projectTitle = "Cableado eléctrico",
            description = "Organización y adecuación del cableado para mejorar la seguridad eléctrica.",
            rating = "5.0",
            review = "Servicio profesional y atención a los detalles."
        )
    }
}

@Composable
private fun ProjectReviewCard(
    imageRes: Int,
    client: String,
    projectTitle: String,
    description: String,
    rating: String,
    review: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 1.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = projectTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(175.dp)
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "CLIENTE:",
                    fontSize = 10.sp,
                    color = PortfolioMuted
                )

                Surface(
                    color = Color(0xFFFFF7E6),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Text(
                        text = "★ $rating",
                        color = Color(0xFFB77900),
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 5.dp
                        )
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = client,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PortfolioText
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = projectTitle,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = PortfolioBlue
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Servicio realizado:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium,
                color = PortfolioMuted
            )

            Text(
                text = description,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = PortfolioText
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 10.dp),
                color = Color(0xFFF1F5F9)
            )

            Text(
                text = "“$review”",
                fontSize = 12.sp,
                color = PortfolioText
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = "Reseña del cliente",
                fontSize = 10.sp,
                color = PortfolioMuted
            )
        }
    }
}
