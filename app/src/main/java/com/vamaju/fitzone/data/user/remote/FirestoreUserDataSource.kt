package com.vamaju.fitzone.data.user.remote

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.classes.model.ClassDto
import com.vamaju.fitzone.data.notifications.model.NotificationDto
import com.vamaju.fitzone.data.user.model.UserDto
import kotlinx.coroutines.tasks.await
import java.util.UUID

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class FirestoreUserDataSource(
    private val firestore: FirebaseFirestore
) : UserDataSource {
    override suspend fun getUser(userId: String): UserDto? {
        return firestore.collection("User")
            .document(userId).get().await()
            .toObject(UserDto::class.java)?.copy(userId = userId)
    }

    override suspend fun updateUserSubscription(
        userId: String,
        subscriptionType: String?,
        startSubscription: Timestamp?
    ) {
        firestore.collection("User").document(userId).update(
            mapOf(
                "subscriptionType" to subscriptionType,
                "startSubscription" to startSubscription
            )
        ).await()
    }

    override suspend fun bookUserClass(userId: String, classId: String) {
        val userRef = firestore.collection("User").document(userId)
        val classRef = firestore.collection("Class").document(classId)
        val notificationsCollectionRef = userRef.collection("Notification")

        firestore.runTransaction { transaction ->
            // Leer los datos más recientes
            val classSnapshot = transaction.get(classRef)
            val userSnapshot = transaction.get(userRef)

            val classData = classSnapshot.toObject(ClassDto::class.java)
            val userData = userSnapshot.toObject(UserDto::class.java)

            if (classData != null && userData != null) {
                // Validar si la clase ya está agendada
                if (classId in userData.classesBooked) {
                    throw Exception("La clase ya ha sido agendada por este usuario.")
                }

                // 1. Incrementar el campo 'booked' en la clase
                val currentBookedCount = classData.booked // Asumiendo que ClassDto tiene un campo 'booked'
                transaction.update(classRef, "booked", currentBookedCount + 1)

                // 2. Añadir el ID de la clase a la colección de clases del usuario
                transaction.update(userRef, "classesBooked", FieldValue.arrayUnion(classId))

                // 3. Crear y añadir la notificación a la subcolección 'notifications'
                val notificationId = UUID.randomUUID().toString()
                val notification = NotificationDto(
                    id = notificationId,
                    title = "¡Clase Agendada!",
                    message = "Has agendado la clase de ${classData.id} el ${classData.dateTime.toDate()}.", // Usar una fecha legible
                    timestamp = Timestamp.now(),
                    isRead = false
                )
                transaction.set(notificationsCollectionRef.document(notificationId), notification)

                // Devolver algo para la transacción, si es necesario
                null
            } else {
                // Si el documento no existe, lanzar una excepción para que la transacción falle
                throw Exception("Class or user document not found.")
            }
        }.await()
    }

}