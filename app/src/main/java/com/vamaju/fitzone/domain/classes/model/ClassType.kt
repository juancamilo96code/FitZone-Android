package com.vamaju.fitzone.domain.classes.model

import com.vamaju.fitzone.data.classes.model.ClassTypeDto

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassType(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
)

fun ClassTypeDto.toDomain(): ClassType {
    return ClassType(
        id = this.id,
        name = this.name,
        description = this.description,
        imageUrl = this.imageUrl
    )
}