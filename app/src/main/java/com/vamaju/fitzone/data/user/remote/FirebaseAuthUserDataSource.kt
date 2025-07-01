package com.vamaju.fitzone.data.user.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.user.model.UserDto
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Singleton
class FirebaseAuthUserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
) : UserAuthDataSource {
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.email
    }

    override suspend fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
        //updateFcmTokenForUser(auth.currentUser?.uid) // Actualiza el token al loguearse
    }

    override suspend fun register(email: String, password: String) {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw Exception("User ID not found after registration.")

        // Crea un documento de usuario inicial en Firestore
        val newUser = UserDto(userId = userId, email = email, classesBooked = emptyList())
        firestore.collection("User").document(email).set(newUser).await()
        //updateFcmTokenForUser(userId) // Actualiza el token al registrarse
    }

    override suspend fun logout() {
        auth.signOut()
    }

    // Función auxiliar para actualizar el token de FCM
   /* private suspend fun updateFcmTokenForUser(userId: String?) {
        if (userId != null) {
            try {
                val token = firebaseMessaging.token.await()
                firestore.collection("users").document(userId).update("fcmToken", token).await()
            } catch (e: Exception) {
                // Log the error, but don't prevent login/registration if token update fails
                println("Error updating FCM token: ${e.message}")
            }
        }
    }*/
}