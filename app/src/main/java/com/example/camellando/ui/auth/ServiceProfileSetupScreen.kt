package com.example.camellando.ui.auth

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ServiceProfileSetupScreen(
    modifier: Modifier = Modifier,
    viewModel: ServiceProfileSetupViewModel = viewModel(),
    onBackClick: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ServiceProfileSetupContent(
        modifier = modifier,
        uiState = uiState,
        onOficioChanged = viewModel::onOficioChanged,
        onExperienciaChanged = viewModel::onExperienciaChanged,
        onPrecioMinChanged = viewModel::onPrecioMinChanged,
        onPrecioMaxChanged = viewModel::onPrecioMaxChanged,
        onArchivoCVSeleccionado = viewModel::onArchivoCVSeleccionado,
        onBackClick = onBackClick,
        onRegistrarClick = {
            if (viewModel.onRegistrarClick()) {
                onRegistrarClick()
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceProfileSetupContent(
    uiState: ServiceProfileSetupUiState,
    onOficioChanged: (String) -> Unit,
    onExperienciaChanged: (String) -> Unit,
    onPrecioMinChanged: (String) -> Unit,
    onPrecioMaxChanged: (String) -> Unit,
    onArchivoCVSeleccionado: (Uri?) -> Unit,
    onBackClick: () -> Unit,
    onRegistrarClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedOficio by remember { mutableStateOf(false) }
    var expandedExperiencia by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val launcherPdf = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        onArchivoCVSeleccionado(uri)
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil de Prestador de Servicio",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
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
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                // Subtítulo / Instrucción
                Text(
                    text = "Completa los datos de tu oficio para publicitar tus servicios",
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                // 1. SELECTOR DE OFICIO
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "OFICIO",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedOficio,
                        onExpandedChange = { expandedOficio = !expandedOficio }
                    ) {
                        OutlinedTextField(
                            value = if (uiState.oficioSeleccionado.isEmpty()) "Selecciona tu oficio" else uiState.oficioSeleccionado,
                            onValueChange = {},
                            readOnly = true,
                            isError = uiState.hayErrorOficio,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                errorBorderColor = MaterialTheme.colorScheme.error
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedOficio,
                            onDismissRequest = { expandedOficio = false }
                        ) {
                            uiState.listaOficios.forEach { oficio ->
                                DropdownMenuItem(
                                    text = { Text(oficio) },
                                    onClick = {
                                        onOficioChanged(oficio)
                                        expandedOficio = false
                                    }
                                )
                            }
                        }
                    }
                    if (uiState.hayErrorOficio) {
                        Text(
                            text = "⚠️ Debes seleccionar un oficio.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // 2. SELECTOR TIEMPO DE EXPERIENCIA
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "TIEMPO DE EXPERIENCIA",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandedExperiencia,
                        onExpandedChange = { expandedExperiencia = !expandedExperiencia }
                    ) {
                        OutlinedTextField(
                            value = if (uiState.experienciaSeleccionada.isEmpty()) "Selecciona tus años de experiencia" else uiState.experienciaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
                            isError = uiState.hayErrorExperiencia,
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0),
                                errorBorderColor = MaterialTheme.colorScheme.error
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedExperiencia,
                            onDismissRequest = { expandedExperiencia = false }
                        ) {
                            uiState.listaExperiencia.forEach { exp ->
                                DropdownMenuItem(
                                    text = { Text(exp) },
                                    onClick = {
                                        onExperienciaChanged(exp)
                                        expandedExperiencia = false
                                    }
                                )
                            }
                        }
                    }
                    if (uiState.hayErrorExperiencia) {
                        Text(
                            text = "⚠️ Debes seleccionar tu tiempo de experiencia.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // 3. CARGAR CV (CARD DE ARCHIVO)
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "CARGAR CV",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.DarkGray
                        )
                        Text(
                            text = "(Opcional)",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }

                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF7F9FC)),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color(0xFFECEFF1), RoundedCornerShape(12.dp))
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Description,
                                        contentDescription = null,
                                        tint = Color(0xFF1E88E5)
                                    )
                                }
                                Column {
                                    Text(
                                        text = uiState.nombreArchivoCV,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = uiState.subdescripcionCV,
                                        fontSize = 11.sp,
                                        color = Color.Gray
                                    )
                                }
                            }

                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = Color.White,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE0E0E0)),
                                modifier = Modifier.clickable {
                                    launcherPdf.launch(arrayOf("application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                                }
                            ) {
                                Text(
                                    text = "Adjuntar",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xFF2563EB),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }

                // 4. RANGO DE PRECIOS
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = "RANGO DE PRECIOS (POR HORA O SERVICIO)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // Mínimo
                        OutlinedTextField(
                            value = uiState.precioMin,
                            onValueChange = onPrecioMinChanged,
                            placeholder = { Text("Mínimo", color = Color.Gray) },
                            trailingIcon = { Text("$", color = Color.Gray, fontWeight = FontWeight.Bold) },
                            isError = uiState.hayErrorPrecio,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )

                        // Máximo
                        OutlinedTextField(
                            value = uiState.precioMax,
                            onValueChange = onPrecioMaxChanged,
                            placeholder = { Text("Máximo", color = Color.Gray) },
                            trailingIcon = { Text("$", color = Color.Gray, fontWeight = FontWeight.Bold) },
                            isError = uiState.hayErrorPrecio,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )
                    }
                    if (uiState.mensajeErrorPrecio != null) {
                        Text(
                            text = "⚠️ ${uiState.mensajeErrorPrecio}",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    } else {
                        Text(
                            text = "Estimado en dólares para mostrar a posibles clientes.",
                            fontSize = 11.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(top = 2.dp)
                        )
                    }
                }

                // Espaciador flexible para empujar el botón al fondo si la pantalla es alta
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(12.dp))

                // MENSAJE DE ERROR GENERAL (SI FALTA ALGÚN CAMPO)
                if (uiState.mensajeErrorGeneral != null) {
                    Text(
                        text = "⚠️ ${uiState.mensajeErrorGeneral}",
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                // BOTÓN PRINCIPAL
                Button(
                    onClick = onRegistrarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !uiState.hayErrorPrecio,
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Registrar",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ServiceProfileSetupScreenPreview() {
    MaterialTheme {
        ServiceProfileSetupContent(
            uiState = ServiceProfileSetupUiState(),
            onOficioChanged = {},
            onExperienciaChanged = {},
            onPrecioMinChanged = {},
            onPrecioMaxChanged = {},
            onArchivoCVSeleccionado = {},
            onBackClick = {},
            onRegistrarClick = {}
        )
    }
}
