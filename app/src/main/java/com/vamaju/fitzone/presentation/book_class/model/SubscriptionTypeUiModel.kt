package com.vamaju.fitzone.presentation.book_class.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class SubscriptionTypeUiModel(
    val id: String,
    val name: String,
    val price: Double,
    val type: String,
    val icon: ImageVector

)
