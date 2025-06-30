package com.vamaju.fitzone.presentation.class_details.model

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassDetails(
    val id: String,
    val typeName: String,
    val date: String,
    val time: String,
    val duration: String,
    val address: String,
    val locationId: String,
    val locationName: String,
    val latitude: Double,
    val longitude: Double,
    val instructorName: String,
)
