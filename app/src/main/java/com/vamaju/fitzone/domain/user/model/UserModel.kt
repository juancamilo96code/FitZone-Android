package com.vamaju.fitzone.domain.user.model

import com.vamaju.fitzone.data.user.model.UserDto
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class UserModel(
    val userId: String,
    val email: String,
    val subscriptionName: String? = null,
    val subscriptionType: String?,
    val startSubscription: Date?,
    val classesBooked: List<String> = emptyList()
)

fun UserDto.toDomain() = UserModel(
    userId = userId,
    email = email,
    subscriptionType = subscriptionType,
    startSubscription = startSubscription?.toDate(),
    classesBooked = classesBooked,
    subscriptionName = subscriptionName
)
