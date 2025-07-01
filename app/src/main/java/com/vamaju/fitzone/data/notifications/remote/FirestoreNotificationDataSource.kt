package com.vamaju.fitzone.data.notifications.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.vamaju.fitzone.data.notifications.model.NotificationDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Singleton
class FirestoreNotificationDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : NotificationDataSource {

    // Obtener el listado de notificaciones del usuario en tiempo real
    override fun getNotifications(userId: String): Flow<List<NotificationDto>> = callbackFlow {
        val notificationsRef = firestore
            .collection("User").document(userId)
            .collection("Notification")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Ordenar por fecha, las más recientes primero

        val subscription = notificationsRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val notifications = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(NotificationDto::class.java)?.copy(id = doc.id)
                }
                trySend(notifications).isSuccess
            }
        }
        awaitClose { subscription.remove() } // Asegurarse de cerrar el listener
    }

    // Marcar una notificación como leída
    override suspend fun markNotificationAsRead(userId: String, notificationId: String) {
        firestore.collection("users").document(userId)
            .collection("notifications").document(notificationId)
            .update("isRead", true)
            .await()
    }
}