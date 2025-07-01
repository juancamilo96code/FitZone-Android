package com.vamaju.fitzone.data.user.remote

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Singleton
class FirebaseAuthUserRepository @Inject constructor(
    private val auth: FirebaseAuth
) : UserAuthDataSource {
    override fun getCurrentUserId(): String? {
        return auth.currentUser?.email
    }
}