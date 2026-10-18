package com.example.camellando.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone


// Lista estática de los 22 cantones de Manabí dentro del mismo archivo
private val cantonesManabi = listOf(
    "24 de Mayo", "Bolívar (Calceta)", "Chone", "El Carmen",
    "Flavio Alfaro", "Jama", "Jaramijó", "Jipijapa",
    "Junín", "Manta", "Montecristi", "Olmedo",
    "Paján", "Pedernales", "Pichincha", "Portoviejo",
    "Puerto López", "Rocafuerte", "San Vicente", "Santa Ana",
    "Sucre (Bahía de Caráquez)", "Tosagua"
)

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onNavigateBack: () -> Unit = {},
    onRegisterAsClient: () -> Unit = {},
    onRegisterAsProvider: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    RegisterContent(
        uiState = uiState,
        onNombreChanged = viewModel::onNombreChanged,
        onApellidoChanged = viewModel::onApellidoChanged,
        onCantonChanged = viewModel::onCantonChanged,
        onNacimientoChanged = viewModel::onNacimientoChanged,
        onIdentificacionChanged = viewModel::onIdentificacionChanged,
        onTipoIdentificacionChanged = viewModel::onTipoIdentificacionChanged,
        onCorreoChanged = viewModel::onCorreoChanged,
        onContrasenaChanged = viewModel::onContrasenaChanged,
        onRepetirContrasenaChanged = viewModel::onRepetirContrasenaChanged,
        onNavigateBack = onNavigateBack,
        onRegisterAsClient = onRegisterAsClient,
        onRegisterAsProvider = onRegisterAsProvider,
        onNavigateToLogin = onNavigateToLogin
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterContent(
    uiState: RegisterUiState,
    onNombreChanged: (String) -> Unit,
    onApellidoChanged: (String) -> Unit,
    onCantonChanged: (String) -> Unit,
    onNacimientoChanged: (String) -> Unit,
    onIdentificacionChanged: (String) -> Unit,
    onTipoIdentificacionChanged: (String) -> Unit,
    onCorreoChanged: (String) -> Unit,
    onContrasenaChanged: (String) -> Unit,
    onRepetirContrasenaChanged: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onRegisterAsClient: () -> Unit,
    onRegisterAsProvider: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    val primaryColor = Color(0xFF1E60F9)

    // Estados locales para visibilidad de contraseña y DatePicker
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isRepeatPasswordVisible by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }

    // Modal de selección de fecha (DatePicker)
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                            timeZone = TimeZone.getTimeZone("UTC")
                        }
                        onNacimientoChanged(formatter.format(Date(millis)))
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar", fontWeight = FontWeight.Bold, color = primaryColor)
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
                title = {},
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.Black)
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
            Text("Registro", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color(0xFF0F172A))
            Text("Ingresa tus datos para comenzar", fontSize = 14.sp, color = Color(0xFF64748B))

            Spacer(modifier = Modifier.height(4.dp))

            // Fila 1: Nombre y Apellido
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CustomTextField(
                    label = "Nombre",
                    value = uiState.nombre,
                    onValueChange = onNombreChanged,
                    placeholder = "Carlos",
                    modifier = Modifier.weight(1f)
                )
                CustomTextField(
                    label = "Apellido",
                    value = uiState.apellido,
                    onValueChange = onApellidoChanged,
                    placeholder = "Mendoza",
                    modifier = Modifier.weight(1f)
                )
            }

            // Fila 2: Cantón (Dropdown Manabí) y Nacimiento (DatePicker)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CantonDropdown(
                    selectedValue = uiState.canton,
                    onValueChange = onCantonChanged,
                    modifier = Modifier.weight(1f)
                )
                CustomTextField(
                    label = "Nacimiento",
                    value = uiState.nacimiento,
                    onValueChange = onNacimientoChanged,
                    placeholder = "dd/mm/aaaa",
                    trailingIcon = Icons.Default.DateRange,
                    onTrailingIconClick = { showDatePicker = true },
                    modifier = Modifier.weight(1f)
                )
            }

            // Fila 3: Identificación + Tipo
            Column {
                Text("Identificación", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
                Spacer(modifier = Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CustomTextField(
                        label = "",
                        value = uiState.identificacion,
                        onValueChange = onIdentificacionChanged,
                        placeholder = "1300000000",
                        keyboardType = KeyboardType.Number,
                        modifier = Modifier.weight(1.8f)
                    )
                    CustomTextField(
                        label = "",
                        value = uiState.tipoIdentificacion,
                        onValueChange = onTipoIdentificacionChanged,
                        trailingIcon = Icons.Default.KeyboardArrowDown,
                        readOnly = true,
                        modifier = Modifier.weight(1.2f)
                    )
                }
            }

            // Correo
            CustomTextField(
                label = "Correo",
                value = uiState.correo,
                onValueChange = onCorreoChanged,
                placeholder = "ejemplo@correo.com",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email
            )

            // Contraseña (con ojo para ver/ocultar)
            CustomTextField(
                label = "Contraseña",
                value = uiState.contrasena,
                onValueChange = onContrasenaChanged,
                placeholder = "••••••••",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                onTrailingIconClick = { isPasswordVisible = !isPasswordVisible },
                isPassword = !isPasswordVisible
            )

            // Repetir Contraseña (con ojo para ver/ocultar)
            CustomTextField(
                label = "Repetir contraseña",
                value = uiState.repetirContrasena,
                onValueChange = onRepetirContrasenaChanged,
                placeholder = "••••••••",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = if (isRepeatPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                onTrailingIconClick = { isRepeatPasswordVisible = !isRepeatPasswordVisible },
                isPassword = !isRepeatPasswordVisible
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botones de acción
            Button(
                onClick = onRegisterAsClient,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Registrar como cliente", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }

            OutlinedButton(
                onClick = onRegisterAsProvider,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(primaryColor))
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ShoppingBag, contentDescription = null, tint = primaryColor, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Registrar como prestador de servicios", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                }
            }

            // Pie de página
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("¿Ya tienes una cuenta? ", fontSize = 13.sp, color = Color(0xFF64748B))
                TextButton(onClick = onNavigateToLogin, contentPadding = PaddingValues(0.dp)) {
                    Text("Iniciar sesión", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = primaryColor)
                }
            }
        }
    }
}

// COMPONENTE: Menú Desplegable de Cantones (Versión Estable)
@Composable
private fun CantonDropdown(
    selectedValue: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Text("Cantón", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
        Spacer(modifier = Modifier.height(4.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedValue,
                onValueChange = {},
                readOnly = true,
                placeholder = { Text("Selecciona", fontSize = 13.sp, color = Color(0xFF94A3B8)) },
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Desplegar cantones",
                            tint = Color(0xFF64748B)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color(0xFFF7F9FC),
                    unfocusedContainerColor = Color(0xFFF7F9FC),
                    focusedBorderColor = Color(0xFF1E60F9),
                    unfocusedBorderColor = Color(0xFFE2E8F0)
                ),
                modifier = Modifier.fillMaxWidth()
            )

            // Capa transparente para detectar el clic en todo el campo
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .noRippleClickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .heightIn(max = 250.dp)
            ) {
                cantonesManabi.forEach { canton ->
                    DropdownMenuItem(
                        text = { Text(canton, fontSize = 14.sp, color = Color(0xFF334155)) },
                        onClick = {
                            onValueChange(canton)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

// COMPONENTE REUTILIZABLE DE CAMPO DE TEXTO
@Composable
private fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: (() -> Unit)? = null,
    readOnly: Boolean = false,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = modifier) {
        if (label.isNotEmpty()) {
            Text(label, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            Spacer(modifier = Modifier.height(4.dp))
        }
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { if (placeholder.isNotEmpty()) Text(placeholder, fontSize = 13.sp, color = Color(0xFF94A3B8)) },
            leadingIcon = leadingIcon?.let { { Icon(it, contentDescription = null, tint = Color(0xFF94A3B8)) } },
            trailingIcon = trailingIcon?.let {
                {
                    if (onTrailingIconClick != null) {
                        IconButton(onClick = onTrailingIconClick) {
                            Icon(it, contentDescription = null, tint = Color(0xFF64748B))
                        }
                    } else {
                        Icon(it, contentDescription = null, tint = Color(0xFF64748B))
                    }
                }
            },
            readOnly = readOnly,
            singleLine = true,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF7F9FC),
                unfocusedContainerColor = Color(0xFFF7F9FC),
                focusedBorderColor = Color(0xFF1E60F9),
                unfocusedBorderColor = Color(0xFFE2E8F0)
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = this.then(
    Modifier.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = onClick
    )
)

// PREVIEW PARA ANDROID STUDIO
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RegisterScreenPreview() {
    MaterialTheme {
        RegisterContent(
            uiState = RegisterUiState(),
            onNombreChanged = {},
            onApellidoChanged = {},
            onCantonChanged = {},
            onNacimientoChanged = {},
            onIdentificacionChanged = {},
            onTipoIdentificacionChanged = {},
            onCorreoChanged = {},
            onContrasenaChanged = {},
            onRepetirContrasenaChanged = {},
            onNavigateBack = {},
            onRegisterAsClient = {},
            onRegisterAsProvider = {},
            onNavigateToLogin = {}
        )
    }
}