package com.vamaju.fitzone.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * @author Juan Camilo Collantes Tovar on 29/06/2025
 * **/

@Serializable
object Splash

@Serializable
object Login

@Serializable
object Register

@Serializable
object Home

@Serializable
data class ClassTypeDetails(
    val classTypeId: String,
)

@Serializable
data class BookClass(
    val classId: String,
)

@Serializable
object MyClasses

@Serializable
object Notifications

@Serializable
object Payments
