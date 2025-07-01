package com.vamaju.fitzone.data.user.remote

import com.google.firebase.Timestamp
import com.vamaju.fitzone.data.user.model.UserDto

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface UserDataSource {
    suspend fun getUser(userId: String): UserDto?
    suspend fun updateUserSubscription(userId: String, subscriptionType: String?, startSubscription: Timestamp?)
    suspend fun bookUserClass(userId: String, classId: String)
}