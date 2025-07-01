package com.vamaju.fitzone.domain.classes.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint
import com.vamaju.fitzone.data.classes.model.ClassDto
import com.vamaju.fitzone.data.classes.model.ClassTypeDto

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassModel(
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

fun ClassDto.toDomain(): ClassModel {
    return ClassModel(
        id = this.id,
        address = this.address,
        booked = this.booked,
        capacity = this.capacity,
        dateTime = this.dateTime,
        duration = this.duration,
        instructorName = this.instructorName,
        location = this.location,
        locationId = this.locationId,
        locationName = this.locationName,
        price = this.price,
        typeClassId = this.typeClassId,
        typeName = this.typeName,

    )
}