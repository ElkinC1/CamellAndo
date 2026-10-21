

package com.example.camellando.ui.feed

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.camellando.R
import com.example.camellando.ui.components.BottomNavigationBar
import com.example.camellando.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel(),
    navController: NavController? = null,
    onNavigateToCreatePost: () -> Unit = {},
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToSearch: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
    onNavigateToProviderProfile: () -> Unit = {},
    onNavigateToChat: () -> Unit = {},
    onNavigateToOfferReceived: () -> Unit = {},
    onNavigate: (Screen) -> Unit = { screen ->
        when (screen) {
            Screen.Chat -> onNavigateToChat()
            Screen.Search -> onNavigateToSearch()
            Screen.ClientProfile -> onNavigateToProfile()
            Screen.OfferReceived -> onNavigateToOfferReceived()
            else -> {}
        }
    }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeContent(
        uiState = uiState,
        navController = navController,
        onPestañaSelected = viewModel::onPestañaSelected,
        onCategoriaSelected = viewModel::onCategoriaSelected,
        onToggleGuardar = viewModel::toggleGuardarServicio,
        onNavigateToCreatePost = onNavigateToCreatePost,
        onNavigateToDetail = onNavigateToDetail,
        onNavigateToSearch = onNavigateToSearch,
        onNavigateToProfile = onNavigateToProfile,
        onNavigateToProviderProfile = onNavigateToProviderProfile,
        onNavigateToChat = onNavigateToChat,
        onNavigateToOfferReceived = onNavigateToOfferReceived,
        onNavigate = onNavigate,
        onDismissAlerta = viewModel::ocultarAlertaPublicacionCreada
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    navController: NavController? = null,
    onPestañaSelected: (Int) -> Unit,
    onCategoriaSelected: (String) -> Unit,
    onToggleGuardar: (String) -> Unit,
    onNavigateToCreatePost: () -> Unit,
    onNavigateToDetail: (String) -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToProviderProfile: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToOfferReceived: () -> Unit = {},
    onNavigate: (Screen) -> Unit = {},
    onDismissAlerta: () -> Unit = {}
) {
    val primaryBlue = Color(0xFF1E60F9)

    // Filtrar publicaciones según pestaña y categoría seleccionada.
    val publicacionesFiltradas = remember(
        uiState.pestañaSeleccionada,
        uiState.categoriaSeleccionada,
        uiState.publicaciones
    ) {
        uiState.publicaciones.filter { servicio ->
            val coincideGuardado =
                if (uiState.pestañaSeleccionada == 1) servicio.esGuardado else true

            val coincideCategoria =
                uiState.categoriaSeleccionada == "Todos" ||
                        servicio.categoria.equals(
                            uiState.categoriaSeleccionada,
                            ignoreCase = true
                        )

            coincideGuardado && coincideCategoria
        }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = primaryBlue,
                                modifier = Modifier.size(32.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        "C",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "CamellAndo",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A)
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToOfferReceived) {
                            BadgedBox(
                                badge = {
                                    Badge(
                                        containerColor = Color(0xFFEF4444),
                                        contentColor = Color.White
                                    ) {
                                        Text(
                                            text = "1",
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Outlined.Notifications,
                                    contentDescription = "Notificaciones",
                                    tint = primaryBlue
                                )
                            }
                        }
                        IconButton(onClick = onNavigateToProfile) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = "Perfil",
                                tint = primaryBlue
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    )
                )

                // Tabs superiores: Para ti y Mis Guardados.
                TabRow(
                    selectedTabIndex = uiState.pestañaSeleccionada,
                    containerColor = Color.White,
                    contentColor = primaryBlue,
                    divider = {}
                ) {
                    Tab(
                        selected = uiState.pestañaSeleccionada == 0,
                        onClick = { onPestañaSelected(0) },
                        text = {
                            Text(
                                "Para ti",
                                fontWeight = if (uiState.pestañaSeleccionada == 0)
                                    FontWeight.Bold else FontWeight.Normal,
                                color = if (uiState.pestañaSeleccionada == 0)
                                    primaryBlue else Color(0xFF94A3B8)
                            )
                        }
                    )

                    Tab(
                        selected = uiState.pestañaSeleccionada == 1,
                        onClick = { onPestañaSelected(1) },
                        text = {
                            Text(
                                "Mis Guardados",
                                fontWeight = if (uiState.pestañaSeleccionada == 1)
                                    FontWeight.Bold else FontWeight.Normal,
                                color = if (uiState.pestañaSeleccionada == 1)
                                    primaryBlue else Color(0xFF94A3B8)
                            )
                        }
                    )
                }

                // Filtros de categorías horizontales.
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.listaCategorias) { categoria ->
                        val isSelected =
                            categoria == uiState.categoriaSeleccionada

                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategoriaSelected(categoria) },
                            label = { Text(categoria, fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = primaryBlue,
                                selectedLabelColor = Color.White,
                                containerColor = Color(0xFFF1F5F9),
                                labelColor = Color(0xFF475569)
                            )
                        )
                    }
                }
            }
        },
        bottomBar = {
            if (navController != null) {
                BottomNavigationBar(navController = navController)
            } else {
                BottomNavigationBar(
                    currentRoute = Screen.Home.route,
                    onNavigate = onNavigate
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToCreatePost,
                containerColor = primaryBlue,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Crear publicación",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = Color(0xFFF8FAFC)
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            if (publicacionesFiltradas.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (uiState.pestañaSeleccionada == 1) {
                                "No tienes publicaciones guardadas aún."
                            } else {
                                "No hay servicios disponibles en esta categoría."
                            },
                            fontSize = 14.sp,
                            color = Color(0xFF64748B)
                        )
                    }
                }
            } else {
                items(publicacionesFiltradas) { servicio ->
                    TarjetaServicioFeed(
                        servicio = servicio,
                        onClick = { onNavigateToDetail(servicio.id) },
                        onToggleGuardar = {
                            onToggleGuardar(servicio.id)
                        },
                        onNavigateToProviderProfile = onNavigateToProviderProfile
                    )
                }
            }
        }

        if (uiState.mostrarAlertaCreada) {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                Toast.makeText(context, "publicación creada", Toast.LENGTH_SHORT).show()
            }

            AlertDialog(
                onDismissRequest = onDismissAlerta,
                title = {
                    Text(
                        text = "publicación creada",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color(0xFF0F172A)
                    )
                },
                text = {
                    Text(
                        text = "Tu publicación ha sido compartida con éxito.",
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = onDismissAlerta,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                    ) {
                        Text("Aceptar", color = Color.White)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }
    }
}

@Composable
private fun  TarjetaServicioFeed(
    servicio: ServicioModel,
    onClick: () -> Unit,
    onToggleGuardar: () -> Unit,
    onNavigateToProviderProfile: () -> Unit
) {
    val primaryBlue = Color(0xFF1E60F9)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Encabezado de la publicación.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (servicio.nombreProveedor.equals("Juan Álvarez", ignoreCase = true)) {
                            onNavigateToProviderProfile()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    Surface(
                        modifier = Modifier.size(46.dp),
                        shape = CircleShape,
                        color = Color(0xFFCBD5E1)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    if (servicio.estaDisponibleHoy) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(
                                    Color(0xFF10B981),
                                    CircleShape
                                )
                                .align(Alignment.BottomEnd)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = servicio.nombreProveedor,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF0F172A)
                        )

                        if (servicio.esVerificado) {
                            Spacer(modifier = Modifier.width(4.dp))

                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Verificado",
                                tint = primaryBlue,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }

                    Text(
                        text = servicio.profesion,
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                // Categoría y botón Guardar.
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = Color(0xFFEFF6FF),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = servicio.categoria,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryBlue,
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                        )
                    }

                    IconButton(onClick = onToggleGuardar) {
                        Icon(
                            imageVector = if (servicio.esGuardado) {
                                Icons.Default.Bookmark
                            } else {
                                Icons.Outlined.BookmarkBorder
                            },
                            contentDescription = "Guardar",
                            tint = if (servicio.esGuardado) {
                                primaryBlue
                            } else {
                                Color(0xFF94A3B8)
                            },
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Precio o tarifa destacada.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Tarifa estimada:",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )

                Text(
                    text = servicio.precio,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = primaryBlue
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = servicio.tituloDescripcion,
                fontSize = 14.sp,
                color = Color(0xFF334155),
                lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Imagen de la publicación.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(210.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE2E8F0))
            ) {
                if (servicio.imagenUri != null) {
                    AsyncImage(
                        model = servicio.imagenUri,
                        contentDescription = "Imagen del trabajo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else if (servicio.imagenResId != null) {
                    Image(
                        painter = painterResource(
                            id = servicio.imagenResId
                        ),
                        contentDescription = "Imagen del trabajo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                // Contador de fotos.
                Surface(
                    color = Color.Black.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "📷 ${servicio.cantidadFotos}",
                        color = Color.White,
                        fontSize = 11.sp,
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 3.dp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = servicio.descripcionDetallada,
                fontSize = 12.sp,
                color = Color(0xFF64748B),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Reacciones y disponibilidad.
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.ThumbUp,
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        servicio.likes.toString(),
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        Icons.Outlined.ChatBubbleOutline,
                        contentDescription = null,
                        tint = Color(0xFF94A3B8),
                        modifier = Modifier.size(16.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        servicio.comentarios.toString(),
                        fontSize = 12.sp,
                        color = Color(0xFF64748B)
                    )
                }

                if (servicio.estaDisponibleHoy) {
                    Surface(
                        color = Color(0xFFECFDF5),
                        shape = RoundedCornerShape(6.dp)
                    ) {
                        Text(
                            text = "Disponible hoy",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF059669),
                            modifier = Modifier.padding(
                                horizontal = 8.dp,
                                vertical = 4.dp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botón principal.
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = primaryBlue
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.QuestionAnswer,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        "Contactar y Cotizar",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun HomeScreenPreview() {
    val sampleState = HomeUiState(
        publicaciones = listOf(
            ServicioModel(
                id = "1",
                nombreProveedor = "Juan Álvarez",
                profesion = "Electricista Matriculado",
                esVerificado = true,
                tiempoPublicacion = "Hace 2 h",
                categoria = "Electricidad",
                precio = "$35.00 / hora",
                tituloDescripcion = "Instalación completa de luminarias LED y readecuación del tablero eléctrico principal.",
                descripcionDetallada = "Materiales ignífugos de primera calidad. Pruebas de carga completadas con éxito.",
                imagenResId = null,
                cantidadFotos = "1/4",
                likes = 38,
                comentarios = 12,
                estaDisponibleHoy = true
            )
        )
    )

    MaterialTheme {
        HomeContent(
            uiState = sampleState,
            onPestañaSelected = {},
            onCategoriaSelected = {},
            onToggleGuardar = {},
            onNavigateToCreatePost = {},
            onNavigateToDetail = {},
            onNavigateToSearch = {},
            onNavigateToProfile = {},
            onNavigateToProviderProfile = {},
            onNavigateToChat = {}
        )
    }
}
