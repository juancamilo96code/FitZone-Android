package com.vamaju.fitzone.data.subscriptions.model

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class SubscriptionTypeDto(
    val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val type: String = "" // MONTH, TRI, YEAR
)
