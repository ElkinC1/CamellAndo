package com.example.camellando.ui.feed

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.camellando.ui.components.BottomNavigationBar
import com.example.camellando.ui.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
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

    // Recargar feed automáticamente al regresar de Crear Publicación o de otra pantalla
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.cargarFeed(forzar = true)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    HomeContent(
        uiState = uiState,
        navController = navController,
        onRefresh = { viewModel.cargarFeed(forzar = true) },
        onPestañaSelected = viewModel::onPestañaSelected,
        onCategoriaSelected = viewModel::onCategoriaSelected,
        onToggleGuardar = viewModel::toggleGuardarServicio,
        onToggleLike = viewModel::toggleLikeServicio,
        onOpenComments = viewModel::abrirComentarios,
        onCerrarComentarios = viewModel::cerrarComentarios,
        onAgregarComentario = viewModel::agregarComentario,
        onOpenMenu = viewModel::abrirMenuOpciones,
        onDismissMenu = viewModel::cerrarMenuOpciones,
        onEditarPublicacion = viewModel::iniciarEdicion,
        onTituloEdicionChanged = viewModel::onTituloEdicionChanged,
        onDescripcionEdicionChanged = viewModel::onDescripcionEdicionChanged,
        onCategoriaEdicionChanged = viewModel::onCategoriaEdicionChanged,
        onPrecioEdicionChanged = viewModel::onPrecioEdicionChanged,
        onTipoTarifaEdicionChanged = viewModel::onTipoTarifaEdicionChanged,
        onNuevasFotosEdicionAgregadas = viewModel::onNuevasFotosEdicionAgregadas,
        onEliminarNuevaFotoEdicion = viewModel::onEliminarNuevaFotoEdicion,
        onEliminarFotoEnEdicion = viewModel::onEliminarFotoEnEdicion,
        onGuardarEdicion = viewModel::guardarEdicion,
        onCancelarEdicion = viewModel::cancelarEdicion,
        onSolicitarEliminar = viewModel::solicitarEliminarPublicacion,
        onConfirmarEliminar = viewModel::confirmarEliminarPublicacion,
        onCancelarEliminar = viewModel::cancelarEliminarPublicacion,
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
    onRefresh: () -> Unit = {},
    onPestañaSelected: (Int) -> Unit,
    onCategoriaSelected: (String) -> Unit,
    onToggleGuardar: (String) -> Unit,
    onToggleLike: (String) -> Unit = {},
    onOpenComments: (ServicioModel) -> Unit = {},
    onCerrarComentarios: () -> Unit = {},
    onAgregarComentario: (String, String) -> Unit = { _, _ -> },
    onOpenMenu: (String) -> Unit = {},
    onDismissMenu: () -> Unit = {},
    onEditarPublicacion: (String) -> Unit = {},
    onTituloEdicionChanged: (String) -> Unit = {},
    onDescripcionEdicionChanged: (String) -> Unit = {},
    onCategoriaEdicionChanged: (String) -> Unit = {},
    onPrecioEdicionChanged: (String) -> Unit = {},
    onTipoTarifaEdicionChanged: (String) -> Unit = {},
    onNuevasFotosEdicionAgregadas: (List<Uri>) -> Unit = {},
    onEliminarNuevaFotoEdicion: (Uri) -> Unit = {},
    onEliminarFotoEnEdicion: (FotoPostModel) -> Unit = {},
    onGuardarEdicion: () -> Unit = {},
    onCancelarEdicion: () -> Unit = {},
    onSolicitarEliminar: (String) -> Unit = {},
    onConfirmarEliminar: () -> Unit = {},
    onCancelarEliminar: () -> Unit = {},
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
                        IconButton(onClick = onRefresh) {
                            Icon(
                                Icons.Default.Refresh,
                                contentDescription = "Actualizar feed",
                                tint = primaryBlue
                            )
                        }
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

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.listaCategorias) { categoria ->
                        val isSelected = categoria == uiState.categoriaSeleccionada

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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (uiState.isLoading && uiState.publicaciones.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primaryBlue)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    if (uiState.errorMessage != null) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = Color(0xFFFEE2E2),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = uiState.errorMessage,
                                        color = Color(0xFFB91C1C),
                                        fontSize = 13.sp,
                                        modifier = Modifier.weight(1f)
                                    )
                                    TextButton(onClick = onRefresh) {
                                        Text("Reintentar", color = primaryBlue)
                                    }
                                }
                            }
                        }
                    }

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
                        items(publicacionesFiltradas, key = { it.id }) { servicio ->
                            TarjetaServicioFeed(
                                servicio = servicio,
                                isMenuExpanded = uiState.menuAbiertoPostId == servicio.id,
                                onClick = { onNavigateToDetail(servicio.id) },
                                onToggleGuardar = { onToggleGuardar(servicio.id) },
                                onToggleLike = { onToggleLike(servicio.id) },
                                onOpenComments = { onOpenComments(servicio) },
                                onOpenMenu = { onOpenMenu(servicio.id) },
                                onDismissMenu = onDismissMenu,
                                onEditar = { onEditarPublicacion(servicio.id) },
                                onEliminar = { onSolicitarEliminar(servicio.id) },
                                onNavigateToProviderProfile = onNavigateToProviderProfile
                            )
                        }
                    }
                }
            }
        }

        // Modal centralizado para editar publicación (detalles y fotos)
        if (uiState.edicionPostState != null) {
            EditarPublicacionDialog(
                state = uiState.edicionPostState,
                listaCategorias = uiState.listaCategorias,
                onTituloChanged = onTituloEdicionChanged,
                onDescripcionChanged = onDescripcionEdicionChanged,
                onCategoriaChanged = onCategoriaEdicionChanged,
                onPrecioChanged = onPrecioEdicionChanged,
                onTipoTarifaChanged = onTipoTarifaEdicionChanged,
                onNuevasFotosAgregadas = onNuevasFotosEdicionAgregadas,
                onEliminarNuevaFoto = onEliminarNuevaFotoEdicion,
                onEliminarFoto = onEliminarFotoEnEdicion,
                onGuardar = onGuardarEdicion,
                onDismiss = onCancelarEdicion
            )
        }

        // Diálogo centralizado para confirmación de eliminación en HomeUiState
        if (uiState.postIdParaEliminar != null) {
            AlertDialog(
                onDismissRequest = onCancelarEliminar,
                title = {
                    Text(
                        text = "Eliminar publicación de prueba",
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = Color(0xFF0F172A)
                    )
                },
                text = {
                    Text(
                        text = "¿Deseas eliminar esta publicación? Se borrará de Supabase junto con todas sus imágenes en Storage para no saturar el almacenamiento.",
                        fontSize = 14.sp,
                        color = Color(0xFF475569)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = onConfirmarEliminar,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDC2626))
                    ) {
                        Text("Eliminar", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = onCancelarEliminar) {
                        Text("Cancelar", color = Color.Gray)
                    }
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }

        // Diálogo para comentarios locales
        if (uiState.postParaComentarios != null) {
            ComentariosDialog(
                post = uiState.postParaComentarios,
                onDismiss = onCerrarComentarios,
                onAgregarComentario = { comentario ->
                    onAgregarComentario(uiState.postParaComentarios.id, comentario)
                }
            )
        }

        if (uiState.mostrarAlertaCreada) {
            val context = LocalContext.current
            LaunchedEffect(Unit) {
                Toast.makeText(context, "Publicación creada con éxito", Toast.LENGTH_SHORT).show()
            }

            AlertDialog(
                onDismissRequest = onDismissAlerta,
                title = {
                    Text(
                        text = "Publicación creada",
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
private fun TarjetaServicioFeed(
    servicio: ServicioModel,
    isMenuExpanded: Boolean = false,
    onClick: () -> Unit,
    onToggleGuardar: () -> Unit,
    onToggleLike: () -> Unit,
    onOpenComments: () -> Unit,
    onOpenMenu: () -> Unit = {},
    onDismissMenu: () -> Unit = {},
    onEditar: () -> Unit = {},
    onEliminar: () -> Unit = {},
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
            // Encabezado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateToProviderProfile() },
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
                                .background(Color(0xFF10B981), CircleShape)
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

                // Categoría, botón Guardar directo y menú de tres puntos (solo publicaciones propias)
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

                    // Botón de guardar con acceso directo de un solo toque
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

                    // Menú de tres puntos (⋮) únicamente cuando la publicación pertenece al usuario logueado
                    if (servicio.esPropia) {
                        Box {
                            IconButton(onClick = onOpenMenu) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Opciones de publicación",
                                    tint = Color(0xFF64748B),
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            DropdownMenu(
                                expanded = isMenuExpanded,
                                onDismissRequest = onDismissMenu
                            ) {
                                DropdownMenuItem(
                                    text = { Text("Editar publicación") },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Edit,
                                            contentDescription = null,
                                            tint = Color(0xFF475569)
                                        )
                                    },
                                    onClick = onEditar
                                )

                                DropdownMenuItem(
                                    text = { Text("Eliminar publicación", color = Color(0xFFDC2626)) },
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Outlined.Delete,
                                            contentDescription = null,
                                            tint = Color(0xFFDC2626)
                                        )
                                    },
                                    onClick = onEliminar
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Tarifa estimada (opcional)
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

            if (servicio.tituloDescripcion.isNotBlank()) {
                Text(
                    text = servicio.tituloDescripcion,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF0F172A),
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            // Imagen(es) de la publicación
            if (servicio.imagenesUrls.isNotEmpty()) {
                val pagerState = rememberPagerState { servicio.imagenesUrls.size }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE2E8F0))
                ) {
                    if (servicio.imagenesUrls.size > 1) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize()
                        ) { page ->
                            AsyncImage(
                                model = servicio.imagenesUrls[page],
                                contentDescription = "Imagen del trabajo ${page + 1}",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Surface(
                            color = Color.Black.copy(alpha = 0.65f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "📷 ${pagerState.currentPage + 1}/${servicio.imagenesUrls.size}",
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    } else {
                        AsyncImage(
                            model = servicio.imagenesUrls.first(),
                            contentDescription = "Imagen del trabajo",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Surface(
                            color = Color.Black.copy(alpha = 0.65f),
                            shape = RoundedCornerShape(6.dp),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "📷 1/1",
                                color = Color.White,
                                fontSize = 11.sp,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            } else if (servicio.imagenUri != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE2E8F0))
                ) {
                    AsyncImage(
                        model = servicio.imagenUri,
                        contentDescription = "Imagen del trabajo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

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
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            } else if (servicio.imagenResId != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFE2E8F0))
                ) {
                    Image(
                        painter = painterResource(id = servicio.imagenResId),
                        contentDescription = "Imagen del trabajo",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            if (servicio.descripcionDetallada.isNotBlank()) {
                Text(
                    text = servicio.descripcionDetallada,
                    fontSize = 13.sp,
                    color = Color(0xFF475569),
                    lineHeight = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Reacciones locales (DataStore) y disponibilidad
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onToggleLike() }
                            .padding(4.dp)
                    ) {
                        Icon(
                            imageVector = if (servicio.esLiked) Icons.Filled.ThumbUp else Icons.Outlined.ThumbUp,
                            contentDescription = "Me gusta",
                            tint = if (servicio.esLiked) primaryBlue else Color(0xFF94A3B8),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            servicio.likes.toString(),
                            fontSize = 12.sp,
                            fontWeight = if (servicio.esLiked) FontWeight.Bold else FontWeight.Normal,
                            color = if (servicio.esLiked) primaryBlue else Color(0xFF64748B)
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onOpenComments() }
                            .padding(4.dp)
                    ) {
                        Icon(
                            Icons.Outlined.ChatBubbleOutline,
                            contentDescription = "Comentarios",
                            tint = Color(0xFF94A3B8),
                            modifier = Modifier.size(18.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            servicio.comentarios.toString(),
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )
                    }
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
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botón Contactar y Cotizar
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
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

@Composable
private fun ComentariosDialog(
    post: ServicioModel,
    onDismiss: () -> Unit,
    onAgregarComentario: (String) -> Unit
) {
    var nuevoComentario by remember { mutableStateOf("") }
    val primaryBlue = Color(0xFF1E60F9)

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Comentarios",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF0F172A)
                )
                IconButton(onClick = onDismiss, modifier = Modifier.size(28.dp)) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Gray)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 350.dp)
            ) {
                if (post.comentariosList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Sé el primero en comentar.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(post.comentariosList) { comentario ->
                            Surface(
                                shape = RoundedCornerShape(10.dp),
                                color = Color(0xFFF1F5F9),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = comentario,
                                    fontSize = 13.sp,
                                    color = Color(0xFF1E293B),
                                    modifier = Modifier.padding(10.dp)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = nuevoComentario,
                        onValueChange = { nuevoComentario = it },
                        placeholder = { Text("Escribe un comentario...", fontSize = 13.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    IconButton(
                        onClick = {
                            if (nuevoComentario.isNotBlank()) {
                                onAgregarComentario(nuevoComentario)
                                nuevoComentario = ""
                            }
                        },
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = primaryBlue)
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Enviar",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {},
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}

@Composable
private fun EditarPublicacionDialog(
    state: EditarPostUiState,
    listaCategorias: List<String>,
    onTituloChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onCategoriaChanged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onTipoTarifaChanged: (String) -> Unit,
    onNuevasFotosAgregadas: (List<Uri>) -> Unit,
    onEliminarNuevaFoto: (Uri) -> Unit,
    onEliminarFoto: (FotoPostModel) -> Unit,
    onGuardar: () -> Unit,
    onDismiss: () -> Unit
) {
    val primaryBlue = Color(0xFF1E60F9)
    val maxNuevas = (3 - state.totalFotos).coerceAtLeast(1)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = maxNuevas)
    ) { uris ->
        if (uris.isNotEmpty()) {
            onNuevasFotosAgregadas(uris)
        }
    }

    val tiposTarifa = listOf("Por trabajo", "Por hora", "Por cita")

    AlertDialog(
        onDismissRequest = { if (!state.isSaving) onDismiss() },
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Editar publicación",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = Color(0xFF0F172A)
                )
                IconButton(onClick = onDismiss, enabled = !state.isSaving) {
                    Icon(Icons.Default.Close, contentDescription = "Cerrar", tint = Color.Gray)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = state.titulo,
                    onValueChange = onTituloChanged,
                    label = { Text("Título") },
                    placeholder = { Text("Título de la publicación") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    singleLine = true,
                    enabled = !state.isSaving
                )

                OutlinedTextField(
                    value = state.descripcion,
                    onValueChange = onDescripcionChanged,
                    label = { Text("Descripción") },
                    placeholder = { Text("Detalla tu servicio o trabajo...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 90.dp),
                    shape = RoundedCornerShape(10.dp),
                    enabled = !state.isSaving
                )

                // Tarifa estimada y modalidad de cobro
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "TARIFA ESTIMADA Y COBRO",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )

                        OutlinedTextField(
                            value = state.precio,
                            onValueChange = onPrecioChanged,
                            label = { Text("Tarifa estimada (USD)") },
                            placeholder = { Text("0.00") },
                            prefix = { Text("$ ") },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                            enabled = !state.isSaving
                        )

                        Text(
                            text = "Modalidad de cobro:",
                            fontSize = 11.sp,
                            color = Color(0xFF64748B)
                        )

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(tiposTarifa) { tipo ->
                                val isSelected = tipo.equals(state.tipoTarifa, ignoreCase = true)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onTipoTarifaChanged(tipo) },
                                    label = { Text(tipo, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = primaryBlue,
                                        selectedLabelColor = Color.White
                                    ),
                                    enabled = !state.isSaving
                                )
                            }
                        }
                    }
                }

                Text(
                    text = "CATEGORÍA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(listaCategorias.filter { it != "Todos" }) { cat ->
                        val isSelected = cat.equals(state.categoria, ignoreCase = true)
                        FilterChip(
                            selected = isSelected,
                            onClick = { onCategoriaChanged(cat) },
                            label = { Text(cat, fontSize = 11.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = primaryBlue,
                                selectedLabelColor = Color.White
                            ),
                            enabled = !state.isSaving
                        )
                    }
                }

                // Sección de fotos (actuales y nuevas)
                Text(
                    text = "FOTOS (${state.totalFotos}/3)",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )

                if (state.totalFotos > 0) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Fotos ya existentes en la publicación
                        items(state.fotos, key = { it.id }) { foto ->
                            Box(modifier = Modifier.size(80.dp)) {
                                AsyncImage(
                                    model = foto.url,
                                    contentDescription = "Foto de publicación",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(10.dp))
                                )

                                IconButton(
                                    onClick = { onEliminarFoto(foto) },
                                    enabled = !state.isSaving,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar foto",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }

                        // Fotos nuevas agregadas durante la edición
                        items(state.nuevasFotos) { uri ->
                            Box(modifier = Modifier.size(80.dp)) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Foto nueva agregada",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(10.dp))
                                )

                                IconButton(
                                    onClick = { onEliminarNuevaFoto(uri) },
                                    enabled = !state.isSaving,
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .background(Color.Black.copy(alpha = 0.65f), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar foto nueva",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    Text(
                        text = "No hay fotos en esta publicación.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }

                // Botón para agregar más fotos si hay cupo (< 3 fotos)
                if (state.totalFotos < 3) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = !state.isSaving) {
                                photoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            },
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF1F5F9),
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                    ) {
                        Row(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AddPhotoAlternate,
                                contentDescription = "Agregar fotos",
                                tint = primaryBlue,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Agregar fotos (${state.totalFotos}/3)",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = primaryBlue
                            )
                        }
                    }
                }

                if (state.errorMessage != null) {
                    Text(
                        text = "⚠️ ${state.errorMessage}",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 12.sp
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onGuardar,
                enabled = !state.isSaving,
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                if (state.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text("Guardar cambios", color = Color.White)
                }
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !state.isSaving) {
                Text("Cancelar", color = Color.Gray)
            }
        },
        shape = RoundedCornerShape(16.dp),
        containerColor = Color.White
    )
}
