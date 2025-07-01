package com.vamaju.fitzone.data.classes.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassDto(
    val id: String = "",
    val address: String = "",
    val booked: Int = 0,
    val capacity: Int = 0,
    val dateTime: Timestamp = Timestamp.now(),
    val duration: Int = 0,
    val instructorName: String = "",
    val location: GeoPoint = GeoPoint(0.0, 0.0),
    val locationId: String = "",
    val locationName: String = "",
    val price: Double = 0.0,
    val typeClassId: String = "",
    val typeName: String = "",
    )
