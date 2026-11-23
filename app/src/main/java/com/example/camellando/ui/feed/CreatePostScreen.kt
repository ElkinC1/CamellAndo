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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage

@Composable
fun CreatePostScreen(
    viewModel: CreatePostViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit = {},
    onPostCreated: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreatePostContent(
        modifier = modifier,
        uiState = uiState,
        onTituloChanged = viewModel::onTituloChanged,
        onDescripcionChanged = viewModel::onDescripcionChanged,
        onCategoriaChanged = viewModel::onCategoriaChanged,
        onPrecioChanged = viewModel::onPrecioChanged,
        onTipoTarifaChanged = viewModel::onTipoTarifaChanged,
        onFotosAgregadas = viewModel::onFotosAgregadas,
        onEliminarFoto = viewModel::onEliminarFoto,
        onTogglePrivacidad = viewModel::onTogglePrivacidad,
        onCloseClick = onCloseClick,
        onPublicarClick = {
            viewModel.publicar {
                onPostCreated()
            }
        },
        onDismissAlertaExito = {
            viewModel.onDismissAlertaExito()
            onPostCreated()
        }
    )
}

@Composable
fun CreatePostContent(
    uiState: CreatePostUiState,
    onTituloChanged: (String) -> Unit,
    onDescripcionChanged: (String) -> Unit,
    onCategoriaChanged: (String) -> Unit,
    onPrecioChanged: (String) -> Unit,
    onTipoTarifaChanged: (String) -> Unit,
    onFotosAgregadas: (List<Uri>) -> Unit,
    onEliminarFoto: (Uri) -> Unit,
    onTogglePrivacidad: () -> Unit,
    onCloseClick: () -> Unit,
    onPublicarClick: () -> Unit,
    onDismissAlertaExito: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)
    ) { uris ->
        if (uris.isNotEmpty()) {
            onFotosAgregadas(uris)
        }
    }

    val scrollState = rememberScrollState()
    val primaryBlue = Color(0xFF1E60F9)

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

                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFE8F0FE)
                ) {
                    Text(
                        text = uiState.estadoPublicacion,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = primaryBlue,
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
                // Info del autor
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(color = Color(0xFF6366F1), shape = CircleShape),
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

                // Selector de categoría
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "CATEGORÍA",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(uiState.categoriasDisponibles) { categoria ->
                            val isSelected = categoria == uiState.categoria
                            FilterChip(
                                selected = isSelected,
                                onClick = { onCategoriaChanged(categoria) },
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

                // Título de la publicación (opcional)
                OutlinedTextField(
                    value = uiState.titulo,
                    onValueChange = onTituloChanged,
                    label = { Text("Título del servicio o trabajo") },
                    placeholder = { Text("Ej: Reparación eléctrica o Avance de obra") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )

                // Descripción
                OutlinedTextField(
                    value = uiState.descripcion,
                    onValueChange = onDescripcionChanged,
                    label = { Text("Descripción") },
                    placeholder = { Text("Describe el trabajo realizado, materiales, garantía o detalles...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 120.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                // Tarifa estimada (opcional)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC))
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "TARIFA ESTIMADA (OPCIONAL)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            )
                            Text(
                                text = "En USD",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Medium,
                                color = primaryBlue
                            )
                        }

                        Text(
                            text = "Puedes dejar la tarifa en blanco para avances de obra o cotizaciones a acordar.",
                            fontSize = 12.sp,
                            color = Color(0xFF64748B)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            OutlinedTextField(
                                value = uiState.precioEstimado,
                                onValueChange = onPrecioChanged,
                                placeholder = { Text("0.00 (opcional)") },
                                prefix = { Text("$ ") },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )
                        }

                        // Modalidad de cobro (por trabajo, por hora, por cita)
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(uiState.tiposTarifaDisponibles) { tipo ->
                                val isSelected = tipo == uiState.tipoTarifa
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onTipoTarifaChanged(tipo) },
                                    label = { Text(tipo, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = primaryBlue,
                                        selectedLabelColor = Color.White,
                                        containerColor = Color(0xFFE2E8F0),
                                        labelColor = Color(0xFF334155)
                                    )
                                )
                            }
                        }
                    }
                }

                // Previsualización de fotos adjuntas (máx 3)
                if (uiState.fotosSeleccionadas.isNotEmpty()) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = "FOTOS ADJUNTAS (${uiState.fotosSeleccionadas.size}/3)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
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
                }

                // Sección para adjuntar fotos
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "AGREGAR A TU PUBLICACIÓN",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray
                    )

                    OpcionAdjuntarItem(
                        icon = Icons.Default.PhotoCamera,
                        title = "Fotos (${uiState.fotosSeleccionadas.size}/3)",
                        bgColor = Color(0xFFE8F5E9),
                        iconTint = Color(0xFF4CAF50),
                        onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                            )
                        }
                    )
                }

                if (uiState.mensajeError != null) {
                    Text(
                        text = "⚠️ ${uiState.mensajeError}",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Botón publicar
                Button(
                    onClick = onPublicarClick,
                    enabled = uiState.puedePublicar && !uiState.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryBlue,
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
