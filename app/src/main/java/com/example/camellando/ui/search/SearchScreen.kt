package com.example.camellando.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.camellando.data.mock.ProviderDummy
import com.example.camellando.data.mock.mockProviders
import com.example.camellando.ui.components.BottomNavigationBar
import com.example.camellando.ui.navigation.Screen

private val SearchBlue = Color(0xFF2563EB)
private val SearchBg = Color(0xFFF5F7FB)
private val SearchMuted = Color(0xFF64748B)

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    onNavigateHome: () -> Unit = {},
    onNavigateMessages: () -> Unit = {},
    onNavigateProfile: () -> Unit = {},
    onNavigateMenu: () -> Unit = {},
    onProviderClick: () -> Unit = {},
    onNavigate: (Screen) -> Unit = { screen ->
        when (screen) {
            Screen.Home -> onNavigateHome()
            Screen.Chat -> onNavigateMessages()
            Screen.ClientProfile -> onNavigateProfile()
            else -> {}
        }
    }
) {
    var query by remember { mutableStateOf("Electricista") }
    var selectedFilter by remember { mutableStateOf("Todos") }
    var priceRange by remember { mutableStateOf("$ - $$$") }

    val providers = remember(query) {
        val source = listOf(
            mockProviders[0],
            ProviderDummy(
                "4", "Roberto Díaz",
                "Plomería y Gasista Integral",
                4.9f, 89, "$20 - $40 / h",
                "Centro Histórico"
            ),
            ProviderDummy(
                "5", "Marcos Carrillo",
                "Climatización & Refrigeración",
                4.7f, 42, "$40 - $70 / h",
                "Zona Norte"
            )
        )

        if (query.isBlank()) {
            source
        } else {
            source.filter {
                it.profession.contains(query, true) ||
                        it.name.contains(query, true) ||
                        query.equals("Electricista", true)
            }.ifEmpty { source }
        }
    }

    Column(
        modifier
            .fillMaxSize()
            .background(SearchBg)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = RoundedCornerShape(18.dp),
                color = Color.White,
                shadowElevation = 2.dp,
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 13.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Buscar",
                        tint = SearchBlue
                    )

                    Spacer(Modifier.width(10.dp))

                    Column(Modifier.weight(1f)) {
                        Text(
                            "OFICIO",
                            fontSize = 10.sp,
                            color = SearchMuted,
                            fontWeight = FontWeight.Bold
                        )

                        BasicTextField(
                            value = query,
                            onValueChange = { query = it },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF111827)
                            )
                        )
                    }

                    Text("│", color = Color.LightGray)
                    Spacer(Modifier.width(8.dp))

                    Column(
                        Modifier.clickable {
                            priceRange =
                                if (priceRange == "$ - $$$") {
                                    "$ - $$"
                                } else {
                                    "$ - $$$"
                                }
                        }
                    ) {
                        Text(
                            "RANGO PRECIO",
                            fontSize = 9.sp,
                            color = SearchMuted,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            priceRange,
                            fontSize = 13.sp,
                            color = SearchBlue,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Icon(
                        Icons.Outlined.ExpandMore,
                        contentDescription = "Cambiar rango de precio",
                        tint = SearchMuted
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Box(
                Modifier
                    .size(46.dp)
                    .background(SearchBlue, CircleShape)
                    .clickable(onClick = onNavigateProfile),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "U",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    Modifier
                        .align(Alignment.BottomEnd)
                        .size(11.dp)
                        .background(Color(0xFF10B981), CircleShape)
                )
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(7.dp)
        ) {
            listOf(
                "Todos",
                "⭐ 4.5+ Estrellas",
                "Disponibles hoy",
                "Cerca"
            ).forEach { filter ->
                val selected = selectedFilter == filter

                Surface(
                    color = if (selected) SearchBlue else Color.White,
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.clickable {
                        selectedFilter = filter
                    }
                ) {
                    Text(
                        filter,
                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 9.dp
                        ),
                        color = if (selected) Color.White else SearchMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1
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
                    top = 25.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Resultados",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Spacer(Modifier.width(10.dp))

            Surface(
                color = Color(0xFFE8F0FF),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    "24 encontrados",
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 5.dp
                    ),
                    color = SearchBlue,
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                "Ordenar por  ⇅",
                color = SearchBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(providers) { provider ->
                ProviderResultCard(provider, onProviderClick)
            }
        }

        if (navController != null) {
            BottomNavigationBar(navController = navController)
        } else {
            BottomNavigationBar(
                currentRoute = Screen.Search.route,
                onNavigate = onNavigate
            )
        }
    }
}

@Composable
private fun ProviderResultCard(
    provider: ProviderDummy,
    onClick: () -> Unit
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(22.dp),
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(62.dp)
                        .background(
                            if (provider.name.startsWith("Juan")) {
                                SearchBlue
                            } else {
                                Color(0xFF0F9AA8)
                            },
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        provider.name
                            .split(" ")
                            .take(2)
                            .mapNotNull { it.firstOrNull() }
                            .joinToString(""),
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            provider.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF111827)
                        )

                        Spacer(Modifier.width(5.dp))

                        Box(
                            Modifier
                                .size(7.dp)
                                .background(Color(0xFF10B981), CircleShape)
                        )
                    }

                    Text(
                        provider.profession,
                        color = SearchBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Outlined.LocationOn,
                            contentDescription = "Ubicación",
                            tint = SearchMuted,
                            modifier = Modifier.size(13.dp)
                        )

                        Text(
                            "A ${
                                if (provider.name.startsWith("Juan")) {
                                    "1.8"
                                } else {
                                    "3.2"
                                }
                            } km • ${provider.city}",
                            color = SearchMuted,
                            fontSize = 10.sp
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        "PRECIOS",
                        fontSize = 9.sp,
                        color = SearchMuted,
                        fontWeight = FontWeight.Bold
                    )

                    Surface(
                        color = Color(0xFFE7F8F0),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            if (provider.name.startsWith("Juan")) {
                                "$ - $$"
                            } else {
                                "$$ - $$$"
                            },
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 5.dp
                            ),
                            color = Color(0xFF16805D),
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }

                    Text(
                        provider.priceRange,
                        fontSize = 10.sp,
                        color = SearchMuted
                    )
                }
            }

            HorizontalDivider(
                Modifier.padding(vertical = 12.dp),
                color = Color(0xFFF0F2F5)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "★★★★★",
                    color = Color(0xFFF5B51B),
                    fontSize = 19.sp
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    "${provider.rating}",
                    color = Color(0xFF111827),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    " (${provider.reviewCount})",
                    color = SearchMuted,
                    fontSize = 11.sp
                )

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = onClick,
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(
                        horizontal = 15.dp,
                        vertical = 8.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                            if (provider.name.startsWith("Juan")) {
                                SearchBlue
                            } else {
                                Color(0xFFF1F5F9)
                            }
                    )
                ) {
                    Text(
                        "Ver perfil  ›",
                        color =
                            if (provider.name.startsWith("Juan")) {
                                Color.White
                            } else {
                                Color(0xFF334155)
                            },
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}
