package com.example.camellando.data.mock

data class ProviderDummy(
    val id: String,
    val name: String,
    val profession: String,
    val rating: Float,
    val reviewCount: Int,
    val priceRange: String
)

val mockProviders = listOf(
    ProviderDummy("1", "Juan Álvarez", "Electricista Matriculado", 4.9f, 48, "$25 - $45 / hr"),
    ProviderDummy("2", "Angela Manzano", "Repostería & Postres", 5.0f, 24, "$15 - $35"),
    ProviderDummy("3", "Roberto Díaz", "Plomería Integral", 4.9f, 89, "$20 - $40 / hr")
)
