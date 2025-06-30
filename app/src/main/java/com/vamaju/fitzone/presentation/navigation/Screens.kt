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
object BookClass

@Serializable
object Payments









@Serializable
object Logout


@Serializable
data class PointRegister(val senderId: String = "")

@Serializable
data class ProcessDetails(
    val vehicleInterventionId: Long,
    val plate: String
)

@Serializable
data class RegisterInProgress(val operationId: Long)

@Serializable
data class RegisterFinished(val operationId: Long)

@Serializable
data class NewRegister(val operationId: Long)

@Serializable
data class PedagogueIntervention(
    val vehicleInterventionId: Long,
    val plate: String
)

@Serializable
data class MechanicReview(
    val vehicleInterventionId: Long,
    val vehicleType: String,
    val plate: String
)

@Serializable
data class FinishIntervention(
    val vehicleInterventionId: Long,
    val plate: String
)

@Serializable
data class Map(val currentPointId: String)

@Serializable
data class GridEvidence(val categoryId: String)

@Serializable
data class EvidenceCheckOutCarrousel(val categoryId: String)