package com.example.camellando.ui.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onNavigateBack: () -> Unit = {},
    onNavigateToLogin: () -> Unit = {},
    onRegisterClientSuccess: (String) -> Unit = {}, // Recibe un mensaje de confirmación
    onRegisterProviderSuccess: () -> Unit = {}      // Redirige al perfil de prestador
) {
    val primaryBlue = Color(0xFF1E60F9)
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Estados del formulario
    var cedula by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repetirContrasena by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
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

            // Campo Identificación
            Text("Identificación", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = cedula,
                onValueChange = { cedula = it },
                placeholder = { Text("1300000000", color = Color(0xFF94A3B8)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            // Campo Correo
            Text("Correo", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                placeholder = { Text("ejemplo@correo.com", color = Color(0xFF94A3B8)) },
                leadingIcon = { Icon(Icons.Default.Mail, contentDescription = null, tint = Color(0xFF94A3B8)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF8FAFC),
                    focusedContainerColor = Color.White
                )
            )

            // Campo Contraseña
            Text("Contraseña", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = contrasena,
                onValueChange = { contrasena = it },
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

            // Campo Repetir Contraseña
            Text("Repetir contraseña", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF334155))
            OutlinedTextField(
                value = repetirContrasena,
                onValueChange = { repetirContrasena = it },
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

            // BOTÓN 1: Registrar como Cliente
            Button(
                onClick = {
                    // Muestra Toast inmediato y redirige al Login
                    Toast.makeText(context, "¡Usuario cliente registrado exitosamente!", Toast.LENGTH_SHORT).show()
                    onRegisterClientSuccess("¡Usuario cliente registrado exitosamente!")
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

            // BOTÓN 2: Registrar como Prestador de Servicios
            OutlinedButton(
                onClick = {
                    onRegisterProviderSuccess()
                },
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

            // Enlace: Iniciar sesión
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
        }
    }
}