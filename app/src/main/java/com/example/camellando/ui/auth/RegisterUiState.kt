package com.example.camellando.ui.auth

data class RegisterUiState(
    val nombre: String = "",
    val apellido: String = "",
    val canton: String = "Selecciona",
    val nacimiento: String = "",
    val identificacion: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val repetirContrasena: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val registerSucceeded: Boolean = false,
    val successRole: com.example.camellando.data.model.UserRole? = null,
    val requiresEmailConfirmation: Boolean = false
)
