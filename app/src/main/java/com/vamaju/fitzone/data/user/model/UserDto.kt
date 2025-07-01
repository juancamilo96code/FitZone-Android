package com.vamaju.fitzone.data.user.model

import com.google.firebase.Timestamp

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class UserDto(
    val userId: String = "",
    val name: String = "",
    val email: String = "",
    val subscriptionName: String? = null,
    val subscriptionType: String? = null,
    val startSubscription: Timestamp? = null,
    val classesBooked: List<String> = emptyList()
)
