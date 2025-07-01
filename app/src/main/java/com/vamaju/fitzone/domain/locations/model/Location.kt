package com.vamaju.fitzone.domain.locations.model

import com.vamaju.fitzone.data.locations.model.LocationDto

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class Location(
    val id: String = "",
    val name: String = "",
)

fun LocationDto.toDomain(): Location {
    return Location(
        id = this.id,
        name = this.name,
    )
}