package com.example.camellando.data.mock

import kotlin.collections.listOf
import com.example.camellando.R
data class ProviderDummy(
    val id: String,
    val name: String,
    val profession: String,
    val rating: Float,
    val reviewCount: Int,
    val priceRange: String,
    val city: String,
    val isActive: Boolean = true,
    val bio: String = ""
)

val mockProviders = listOf(
    ProviderDummy("1", "Juan Álvarez", "Electricista Matriculado", 4.9f, 48, "$25 - $45 / hr", city = "Manta, Ecuador", isActive = true, bio = "Instalaciones y reparaciones eléctricas residenciales."),
    ProviderDummy("2", "Angela Manzano", "Repostería & Postres", 5.0f, 24, "$15 - $35", city = "Manta, Ecuador", isActive = true, bio = "Preparación de postres para eventos y ocasiones especiales."),
    ProviderDummy("3", "Roberto Díaz", "Plomería Integral", 4.9f, 89, "$20 - $40 / hr", city = "Manta, Ecuador", isActive = false, bio = "Servicios de mantenimiento y reparación de instalaciones de agua.")
)


data class ReviewDummy(
    val id: String,
    val username: String,
    val rating: Int,
    val description: String
)

val mockReviews = listOf(
    ReviewDummy(id = "1", username = "María López", rating = 5, description = "Excelente trabajo, muy puntual y profesional. ¡Lo recomiendo!"),
    ReviewDummy(id = "2", username = "Carlos Mendoza", rating = 4, description = "Buen servicio y atención. Terminó el trabajo a tiempo."),
    ReviewDummy(id = "3", username = "Sofía García", rating = 5, description = "Muy amable y cuidadoso con los detalles. Quedé satisfecha.")
)


data class PostDummy(
    val id: String,
    val providerName: String,
    val date: String,
    val description: String,
    val imageRes: List<Int> = emptyList()
)

val mockPosts: List<PostDummy> by lazy {
    listOf(
        PostDummy(id = "1", providerName = "Juan Álvarez", date = "18 oct. 2026", description = "Instalación eléctrica terminada. Revisamos cada conexión para garantizar un trabajo seguro y de calidad.", imageRes = listOf(R.drawable.portfolio_electrical, R.drawable.portfolio_lighting, R.drawable.portfolio_wiring)),
        PostDummy(id = "2", providerName = "Juan Álvarez", date = "15 oct. 2026", description = "¿Necesitas renovar la iluminación de tu hogar? Puedo ayudarte a encontrar una solución funcional y moderna.", imageRes = listOf(R.drawable.portfolio_lighting)),
        PostDummy(id = "3", providerName = "Juan Álvarez", date = "12 oct. 2026", description = "Recuerda revisar periódicamente las instalaciones eléctricas de tu hogar para prevenir posibles inconvenientes.")
    )
}