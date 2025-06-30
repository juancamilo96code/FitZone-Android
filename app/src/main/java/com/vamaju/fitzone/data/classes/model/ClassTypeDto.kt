package com.vamaju.fitzone.data.classes.model

import com.google.firebase.firestore.PropertyName

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class ClassTypeDto(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    var imageUrl: String = ""
)
