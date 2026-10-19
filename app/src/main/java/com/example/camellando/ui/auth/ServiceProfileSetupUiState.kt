package com.example.camellando.ui.auth

import android.net.Uri
import com.example.camellando.data.mock.listaExperiencia
import com.example.camellando.data.mock.listaOficios

data class ServiceProfileSetupUiState(
    val oficioSeleccionado: String = "",
    val experienciaSeleccionada: String = "",
    val precioMin: String = "",
    val precioMax: String = "",
    val nombreArchivoCV: String = "PDF, DOCX (Máx. 5MB)",
    val subdescripcionCV: String = "Adjunta tus certificados o CV",
    val cvUri: Uri? = null,
    val haInteractuadoConPrecios: Boolean = false,
    val hayErrorPrecio: Boolean = false,
    val mensajeErrorPrecio: String? = null,
    val hayErrorOficio: Boolean = false,
    val hayErrorExperiencia: Boolean = false,
    val mensajeErrorGeneral: String? = null,
    val isLoading: Boolean = false,
    val listaOficios: List<String> = com.example.camellando.data.mock.listaOficios,
    val listaExperiencia: List<String> = com.example.camellando.data.mock.listaExperiencia
)
