package com.example.camellando.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onRegisterClientSuccess: (String) -> Unit = {},
    onRegisterProviderSuccess: () -> Unit = {}
) {
    val primaryBlue = Color(0xFF1E60F9)
    val context = LocalContext.current

    // Estado del formulario
    var state by remember { mutableStateOf(RegisterUiState()) }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var expandedCanton by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    val listaCantones = listOf("Manta", "Portoviejo", "Jaramijó", "Montecristi", "Chone", "Otro")

    // Diálogo Selector de Fecha Nativo de Material 3
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }
                            val fechaFormateada = formatter.format(Date(millis))
                            state = state.copy(nacimiento = fechaFormateada)
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("Aceptar", color = primaryBlue, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar", color = Color(0xFF64748B))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF0F172A))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0F172A)
            )

            // Nombres y Apellidos
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Nombre", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.nombre,
                        onValueChange = { state = state.copy(nombre = it) },
                        placeholder = { Text("Ej. Juan", color = Color(0xFF94A3B8), fontSize = 13.sp) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color.White
                        )
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text("Apellido", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.apellido,
                        onValueChange = { state = state.copy(apellido = it) },
                        placeholder = { Text("Ej. Pérez", color = Color(0xFF94A3B8), fontSize = 13.sp) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Cantón y Fecha de Nacimiento
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Cantón", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                    Spacer(modifier = Modifier.height(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedCanton,
                        onExpandedChange = { expandedCanton = !expandedCanton }
                    ) {
                        OutlinedTextField(
                            value = state.canton,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCanton) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedContainerColor = Color(0xFFF8FAFC),
                                focusedContainerColor = Color.White
                            ),
                            modifier = Modifier.menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedCanton,
                            onDismissRequest = { expandedCanton = false }
                        ) {
                            listaCantones.forEach { cantonSeleccionado ->
                                DropdownMenuItem(
                                    text = { Text(cantonSeleccionado) },
                                    onClick = {
                                        state = state.copy(canton = cantonSeleccionado)
                                        expandedCanton = false
                                    }
                                )
                            }
                        }
                    }
                }

                Column(modifier = Modifier.weight(1f)) {
                    Text("Nacimiento", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = state.nacimiento,
                        onValueChange = { state = state.copy(nacimiento = it) },
                        placeholder = { Text("DD/MM/AAAA", color = Color(0xFF94A3B8), fontSize = 12.sp) },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker = true }) {
                                Icon(
                                    Icons.Default.CalendarToday,
                                    contentDescription = "Seleccionar fecha",
                                    tint = primaryBlue,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFF8FAFC),
                            focusedContainerColor = Color.White
                        )
                    )
                }
            }

            // Identificación (Cédula)
            Text("Identificación", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = state.identificacion,
                onValueChange = { state = state.copy(identificacion = it) },
                placeholder = { Text("1300000000", color = Color(0xFF94A3B8)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            // Correo
            Text("Correo", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = state.correo,
                onValueChange = { state = state.copy(correo = it) },
                placeholder = { Text("ejemplo@correo.com", color = Color(0xFF94A3B8)) },
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = Color(0xFF94A3B8)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            // Contraseña
            Text("Contraseña", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = state.contrasena,
                onValueChange = { state = state.copy(contrasena = it) },
                placeholder = { Text("••••••••", color = Color(0xFF94A3B8)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF94A3B8)) },
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            imageVector = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color(0xFF94A3B8)
                        )
                    }
                },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            // Repetir Contraseña
            Text("Repetir contraseña", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = state.repetirContrasena,
                onValueChange = { state = state.copy(repetirContrasena = it) },
                placeholder = { Text("••••••••", color = Color(0xFF94A3B8)) },
                leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = Color(0xFF94A3B8)) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Botón 1: Registrar como cliente
            Button(
                onClick = {
                    // Validaciones de Campos Vacíos y Arroba
                    val hayCamposVacios = state.nombre.isBlank() ||
                            state.apellido.isBlank() ||
                            state.canton == "Selecciona" ||
                            state.nacimiento.isBlank() ||
                            state.identificacion.isBlank() ||
                            state.correo.isBlank() ||
                            state.contrasena.isBlank() ||
                            state.repetirContrasena.isBlank()

                    if (hayCamposVacios) {
                        Toast.makeText(context, "Un campo está vacío", Toast.LENGTH_SHORT).show()
                    } else if (!state.correo.contains("@")) {
                        Toast.makeText(context, "El correo debe contener un '@'", Toast.LENGTH_SHORT).show()
                    } else if (state.contrasena != state.repetirContrasena) {
                        Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "¡Usuario cliente registrado exitosamente!", Toast.LENGTH_SHORT).show()
                        onRegisterClientSuccess("¡Usuario cliente registrado exitosamente!")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Registrar como cliente", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.Default.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }

            // Botón 2: Registrar como prestador de servicios
            OutlinedButton(
                onClick = onRegisterProviderSuccess,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.SolidColor(primaryBlue)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.WorkOutline, contentDescription = null, tint = primaryBlue, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Registrar como prestador de servicios", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = primaryBlue)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Ya tienes una cuenta? ", fontSize = 13.sp, color = Color(0xFF64748B))
                TextButton(onClick = onNavigateToLogin, contentPadding = PaddingValues(0.dp)) {
                    Text("Iniciar sesión", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = primaryBlue)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}