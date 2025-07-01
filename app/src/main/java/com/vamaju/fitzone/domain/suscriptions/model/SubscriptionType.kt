package com.vamaju.fitzone.domain.suscriptions.model

import com.vamaju.fitzone.data.subscriptions.model.SubscriptionTypeDto

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class SubscriptionType(
    val id: String,
    val name: String,
    val price: Double,
    val type: String // MONTH, TRI, YEAR
)

fun SubscriptionTypeDto.toDomain() = SubscriptionType(
    id = id,
    name = name,
    price = price,
    type = type
)