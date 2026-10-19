package com.example.camellando.ui.negotiation

import androidx.annotation.DrawableRes
import com.example.camellando.data.mock.ProviderDummy

data class PrestadorContraOfertaModel(
    val id: String = "1",
    val nombre: String = "Juan Álvarez",
    val profesion: String = "Electricista Matriculado",
    val rol: String = "PRESTADOR DE SERVICIOS",
    val calificacion: Float = 4.9f,
    val totalResenas: Int = 48,
    val ubicacion: String = "Manta, Ecuador • A 2.4 km de ti",
    val estaEnLinea: Boolean = true,
    @get:DrawableRes val fotoPerfilResId: Int? = null
)

typealias ContraOfertaModel = PrestadorContraOfertaModel

fun ProviderDummy.toPrestadorContraOferta(
    @DrawableRes fotoPerfilResId: Int? = null,
    distanciaKm: String = "A 2.4 km de ti"
): PrestadorContraOfertaModel = PrestadorContraOfertaModel(
    id = id,
    nombre = name,
    profesion = profession,
    rol = "PRESTADOR DE SERVICIOS",
    calificacion = rating,
    totalResenas = reviewCount,
    ubicacion = "$city • $distanciaKm",
    estaEnLinea = isActive,
    fotoPerfilResId = fotoPerfilResId
)

data class ContraOfertaDetalleModel(
    val id: String = "co101",
    val tiempoTranscurrido: String = "Hace 3 min",
    val tituloServicio: String = "Instalación eléctrica y tablero bifásico",
    val descripcionServicio: String = "Puedo realizar el trabajo asegurando materiales certificados, revisión del panel principal y balanceo de carga.",
    val fechaHora: String = "12/08/2026, 09:00 AM",
    val categoria: String = "Electricidad",
    val presupuestoOfrecido: String = "$75.00",
    val presupuestoOriginal: String = "$65.00",
    val moneda: String = "USD",
    val prestador: PrestadorContraOfertaModel = PrestadorContraOfertaModel()
) {
    val cliente: PrestadorContraOfertaModel get() = prestador
}

data class CounterOfferReceivedUiState(
    val contraoferta: ContraOfertaDetalleModel = ContraOfertaDetalleModel(),
    val isLoading: Boolean = false,
    val mensajeError: String? = null,
    val contraofertaAceptada: Boolean = false,
    val contraofertaRechazada: Boolean = false,
    val abrirModalNuevaContraoferta: Boolean = false,
    val mostrarDialogo: Boolean = true,
    val mostrarAlertaExito: Boolean = false
) {
    val puedeNegociar: Boolean
        get() = !isLoading && mostrarDialogo
}

typealias CounterOfferUiState = CounterOfferReceivedUiState
typealias ContraOfertaRecibidaUiState = CounterOfferReceivedUiState
