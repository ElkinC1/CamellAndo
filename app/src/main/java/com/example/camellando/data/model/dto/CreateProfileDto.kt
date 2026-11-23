package com.example.camellando.data.model.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** Profile fields sent as Supabase Auth metadata for the database trigger to insert. */
@Serializable
data class CreateProfileDto(
    @SerialName("display_name")
    val displayName: String,
    val role: String,
    val location: String,
    @SerialName("birth_date")
    val birthDate: String,
    val identification: String,
    val occupation: String? = null,
    @SerialName("experience_years")
    val experienceYears: String? = null,
    @SerialName("min_price")
    val minPrice: Double? = null,
    @SerialName("max_price")
    val maxPrice: Double? = null
) {
    fun toAuthMetadata() = buildJsonObject {
        put("display_name", displayName)
        put("role", role)
        put("location", location)
        put("birth_date", birthDate)
        put("identification", identification)
        occupation?.let { put("occupation", it) }
        experienceYears?.let { put("experience_years", it) }
        minPrice?.let { put("min_price", it) }
        maxPrice?.let { put("max_price", it) }
    }
}
