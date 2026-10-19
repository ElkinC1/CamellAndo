package com.example.camellando.ui.feed

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun CreatePostScreen(
    modifier: Modifier = Modifier,
    viewModel: CreatePostViewModel = viewModel(),
    onCloseClick: () -> Unit = {},
    onPublicarClick: (texto: String, imagenes: List<Uri>) -> Unit = { _, _ -> }
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreatePostContent(
        modifier = modifier,
        uiState = uiState,
        onTextoChanged = viewModel::onTextoChanged,
        onFotosAgregadas = viewModel::onFotosAgregadas,
        onEliminarFoto = viewModel::onEliminarFoto,
        onVideoAgregado = viewModel::onVideoAgregado,
        onEliminarVideo = viewModel::onEliminarVideo,
        onTogglePrivacidad = viewModel::onTogglePrivacidad,
        onCloseClick = onCloseClick,
        onPublicarClick = {
            if (viewModel.onPublicarClick()) {
                val texto = uiState.textoPublicacion
                val fotos = uiState.fotosSeleccionadas
                viewModel.reiniciarEstado()
                onPublicarClick(texto, fotos)
            }
        },
        onDismissAlertaExito = {
            viewModel.onDismissAlertaExito()
            onPublicarClick(uiState.textoPublicacion, uiState.fotosSeleccionadas)
        }
    )
}

@Composable
fun CreatePostContent(
    uiState: CreatePostUiState,
    onTextoChanged: (String) -> Unit,
    onFotosAgregadas: (List<Uri>) -> Unit,
    onEliminarFoto: (Uri) -> Unit,
    onVideoAgregado: (Uri?) -> Unit,
    onEliminarVideo: (Uri) -> Unit,
    onTogglePrivacidad: () -> Unit,
    onCloseClick: () -> Unit,
    onPublicarClick: () -> Unit,
    onDismissAlertaExito: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    // Launcher nativo para seleccionar MÚLTIPLES FOTOS de la galería
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            onFotosAgregadas(uris)
        }
    }

    // Launcher nativo para seleccionar VIDEO
    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let { onVideoAgregado(it) }
    }

    val scrollState = rememberScrollState()

    Scaffold(
        modifier = modifier.imePadding(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onCloseClick) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cerrar",
                        tint = Color.DarkGray
                    )
                }

                Text(
                    text = "Crear publicación",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                // Chip de estado "Borrador"
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F0FE)
                ) {
                    Text(
                        text = uiState.estadoPublicacion,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF2563EB),
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }
        },
        containerColor = Color.White
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // INFO DEL USUARIO
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Avatar con iniciales
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(
                                color = Color(0xFF6366F1),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = uiState.autorInicial,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = uiState.autor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )

                        // Selector de Privacidad
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFF3F4F6),
                            modifier = Modifier.clickable { onTogglePrivacidad() }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = if (uiState.esPublico) Icons.Default.Public else Icons.Default.Lock,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Gray
                                )
                                Text(
                                    text = if (uiState.esPublico) "Para: Público" else "Para: Solo yo",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(14.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }

                // ÁREA DE TEXTO PRINCIPAL
                TextField(
                    value = uiState.textoPublicacion,
                    onValueChange = onTextoChanged,
                    placeholder = {
                        Text(
                            "Escribe algo...",
                            color = Color.LightGray,
                            fontSize = 16.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                // PREVISUALIZACIÓN DE FOTOS ADJUNTADAS
                if (uiState.fotosSeleccionadas.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(uiState.fotosSeleccionadas) { uri ->
                            Box(modifier = Modifier.size(90.dp)) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = "Foto adjunta",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(12.dp))
                                )
                                // Botón para eliminar foto
                                IconButton(
                                    onClick = { onEliminarFoto(uri) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
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
                    }
                }

                // PREVISUALIZACIÓN DE VIDEOS ADJUNTADOS
                if (uiState.videosSeleccionados.isNotEmpty()) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(uiState.videosSeleccionados) { uri ->
                            Box(
                                modifier = Modifier
                                    .size(90.dp)
                                    .background(Color.Black, RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                // Icono para indicar que es un video
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Video adjunto",
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )

                                // Botón para eliminar el video de la lista
                                IconButton(
                                    onClick = { onEliminarVideo(uri) },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .size(24.dp)
                                        .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Eliminar video",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // SECCIÓN "AGREGAR A TU PUBLICACIÓN"
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "AGREGAR A TU PUBLICACIÓN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    // Opción: Fotos (Abre el Photo Picker)
                    OpcionAdjuntarItem(
                        icon = Icons.Default.PhotoCamera,
                        title = "Fotos",
                        bgColor = Color(0xFFE8F5E9),
                        iconTint = Color(0xFF4CAF50),
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )

                    // Opción: Videos
                    OpcionAdjuntarItem(
                        icon = Icons.Default.Videocam,
                        title = "Videos",
                        bgColor = Color(0xFFFFEBEE),
                        iconTint = Color(0xFFE53935),
                        onClick = {
                            videoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.VideoOnly)
                            )
                        }
                    )

                    // Opción: Ubicación
                    OpcionAdjuntarItem(
                        icon = Icons.Default.LocationOn,
                        title = "Ubicación",
                        bgColor = Color(0xFFFFF8E1),
                        iconTint = Color(0xFFFBC02D),
                        onClick = { /* Abrir selector de ubicación */ }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // MENSAJE DE ERROR (SI APLICA)
                if (uiState.mensajeError != null) {
                    Text(
                        text = "⚠️ ${uiState.mensajeError}",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // BOTÓN PUBLICAR
                Button(
                    onClick = onPublicarClick,
                    enabled = uiState.puedePublicar && !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF2563EB),
                        disabledContainerColor = Color(0xFF93C5FD)
                    )
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = "PUBLICAR",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    if (uiState.mostrarAlertaExito) {
        AlertDialog(
            onDismissRequest = onDismissAlertaExito,
            title = {
                Text(
                    text = "¡Publicación creada!",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = "Tu publicación ha sido compartida con éxito.",
                    fontSize = 14.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = onDismissAlertaExito,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Text("Aceptar", color = Color.White)
                }
            },
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White
        )
    }
}

// COMPONENTE DE FILA REUTILIZABLE PARA OPCIONES
@Composable
fun OpcionAdjuntarItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    bgColor: Color,
    iconTint: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(bgColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(
                text = title,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black
            )
        }

        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = Color.LightGray
        )
    }
}

@Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun CreatePostScreenPreview() {
    MaterialTheme {
        CreatePostContent(
            uiState = CreatePostUiState(),
            onTextoChanged = {},
            onFotosAgregadas = {},
            onEliminarFoto = {},
            onVideoAgregado = {},
            onEliminarVideo = {},
            onTogglePrivacidad = {},
            onCloseClick = {},
            onPublicarClick = {}
        )
    }
}

@Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun CrearPublicacionScreenPreview() {
    CreatePostScreenPreview()
}
