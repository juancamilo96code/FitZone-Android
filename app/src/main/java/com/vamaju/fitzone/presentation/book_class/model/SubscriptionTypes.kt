package com.vamaju.fitzone.presentation.book_class.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
data class SubscriptionTypes(
    val id: Int,
    val name: String,
    val icon: ImageVector,
    val price: Double,
    val type: String,
)
