package com.vamaju.fitzone.domain.user

import com.vamaju.fitzone.domain.user.model.UserModel
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface UserRepository {
    fun getCurrentUserId(): String?
    suspend fun getUser(userId: String): UserModel?
    suspend fun updateSubscription(userId: String, subscriptionType: String, startDate: Date?)
    suspend fun bookClass(userId: String, classId: String)
}