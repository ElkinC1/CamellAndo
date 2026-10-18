package com.example.camellando

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.ui.tooling.preview.Preview
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.camellando.data.mock.listaOficios
import com.example.camellando.data.mock.listaExperiencia
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilPrestadorScreen(
    onBackClick: () -> Unit = {},
    onRegistrarClick: () -> Unit = {}
) {
    // --- ESTADOS MOCK DE NAVEGACIÓN Y CAMPOS ---
    var oficioSeleccionado by remember { mutableStateOf("") }
    var experienciaSeleccionada by remember { mutableStateOf("") }
    var precioMin by remember { mutableStateOf("") }
    var precioMax by remember { mutableStateOf("") }
    var nombreArchivoCV by remember { mutableStateOf("PDF, DOCX (Máx. 5MB)") }
    var subdescripcionCV by remember { mutableStateOf("Adjunta tus certificados o CV") }

    var expandedOficio by remember { mutableStateOf(false) }
    var expandedExperiencia by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()
    val launcherPdf = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let { selectedUri ->
            nombreArchivoCV = selectedUri.lastPathSegment ?: "documento_seleccionado.pdf"
            subdescripcionCV = "Archivo adjuntado con éxito"
        }
    }
    Scaffold(
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
        // Box y Column responsivos con scroll vertical
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
                            value = if (oficioSeleccionado.isEmpty()) "Selecciona tu oficio" else oficioSeleccionado,
                            onValueChange = {},
                            readOnly = true,
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
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedOficio,
                            onDismissRequest = { expandedOficio = false }
                        ) {
                            listaOficios.forEach { oficio ->
                                DropdownMenuItem(
                                    text = { Text(oficio) },
                                    onClick = {
                                        oficioSeleccionado = oficio
                                        expandedOficio = false
                                    }
                                )
                            }
                        }
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
                            value = if (experienciaSeleccionada.isEmpty()) "Selecciona tus años de experiencia" else experienciaSeleccionada,
                            onValueChange = {},
                            readOnly = true,
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
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )
                        ExposedDropdownMenu(
                            expanded = expandedExperiencia,
                            onDismissRequest = { expandedExperiencia = false }
                        ) {
                            listaExperiencia.forEach { exp ->
                                DropdownMenuItem(
                                    text = { Text(exp) },
                                    onClick = {
                                        experienciaSeleccionada = exp
                                        expandedExperiencia = false
                                    }
                                )
                            }
                        }
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
                                        text = nombreArchivoCV,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color.DarkGray
                                    )
                                    Text(
                                        text = subdescripcionCV,
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
                            value = precioMin,
                            onValueChange = { precioMin = it },
                            placeholder = { Text("Mínimo", color = Color.Gray) },
                            trailingIcon = { Text("$", color = Color.Gray, fontWeight = FontWeight.Bold) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )

                        // Máximo
                        OutlinedTextField(
                            value = precioMax,
                            onValueChange = { precioMax = it },
                            placeholder = { Text("Máximo", color = Color.Gray) },
                            trailingIcon = { Text("$", color = Color.Gray, fontWeight = FontWeight.Bold) },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFFE0E0E0)
                            )
                        )
                    }

                    Text(
                        text = "Estimado en dólares para mostrar a posibles clientes.",
                        fontSize = 11.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }

                // Espaciador flexible para empujar el botón al fondo si la pantalla es alta
                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))

                // BOTÓN PRINCIPAL
                Button(
                    onClick = onRegistrarClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
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


@Preview(showBackground = true, widthDp = 392, heightDp = 800)
@Composable
fun PerfilPrestadorScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        PerfilPrestadorScreen()
    }
}