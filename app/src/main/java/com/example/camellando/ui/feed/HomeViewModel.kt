package com.example.camellando.ui.feed

import androidx.lifecycle.ViewModel
import com.example.camellando.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        cargarPublicacionesPrueba()
    }

    fun onPestañaSelected(index: Int) {
        _uiState.update { it.copy(pestañaSeleccionada = index) }
    }

    fun onCategoriaSelected(categoria: String) {
        _uiState.update { it.copy(categoriaSeleccionada = categoria) }
    }

    fun toggleGuardarServicio(servicioId: String) {
        _uiState.update { state ->
            val listaActualizada = state.publicaciones.map { servicio ->
                if (servicio.id == servicioId) {
                    servicio.copy(esGuardado = !servicio.esGuardado)
                } else {
                    servicio
                }
            }
            state.copy(publicaciones = listaActualizada)
        }
    }

    private fun cargarPublicacionesPrueba() {
        val datosPrueba = listOf(
            // Publicación 1: Electricista
            ServicioModel(
                id = "1",
                nombreProveedor = "Juan Álvarez",
                profesion = "Electricista Matriculado",
                esVerificado = true,
                tiempoPublicacion = "Hace 2 h",
                categoria = "Electricidad",
                precio = "$35.00 / hora",
                tituloDescripcion = "Instalación completa de luminarias LED, renovación de circuitos y readecuación del tablero eléctrico principal en residencia unifamiliar.",
                descripcionDetallada = "Materiales ignífugos de primera calidad, certificados bajo norma IRAM. Pruebas de carga y disyuntores completadas con éxito.",
                imagenResId = R.drawable.electricista,
                cantidadFotos = "1/4",
                likes = 38,
                comentarios = 12,
                estaDisponibleHoy = true,
                esGuardado = false
            ),
            // Publicación 2: Plomero
            ServicioModel(
                id = "2",
                nombreProveedor = "Carlos Mendoza",
                profesion = "Plomero Certificado",
                esVerificado = true,
                tiempoPublicacion = "Hace 4 h",
                categoria = "Plomería",
                precio = "$25.00 / visita",
                tituloDescripcion = "Reparación urgente de fugas en tuberías de agua potable, destape de cañerías e instalación de grifería moderna.",
                descripcionDetallada = "Uso de herramientas de termofusión y detección de fugas sin romper paredes. Garantía de 6 meses en todos los trabajos.",
                imagenResId = R.drawable.plomero,
                cantidadFotos = "1/3",
                likes = 24,
                comentarios = 8,
                estaDisponibleHoy = true,
                esGuardado = false
            )
        )
        _uiState.update { it.copy(publicaciones = datosPrueba) }
    }
}