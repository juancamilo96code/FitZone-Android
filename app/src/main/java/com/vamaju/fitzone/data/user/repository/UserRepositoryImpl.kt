package com.vamaju.fitzone.data.user.repository

import com.google.firebase.Timestamp
import com.vamaju.fitzone.data.user.remote.UserAuthDataSource
import com.vamaju.fitzone.data.user.remote.UserDataSource
import com.vamaju.fitzone.domain.user.UserRepository
import com.vamaju.fitzone.domain.user.model.UserModel
import com.vamaju.fitzone.domain.user.model.toDomain
import java.util.Date
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: UserDataSource,
    private val userAuthDataSource: UserAuthDataSource
) : UserRepository {
    override fun getCurrentUserId(): String? {
        return userAuthDataSource.getCurrentUserId()
    }

    override suspend fun getUser(userId: String): UserModel? {
        return remoteDataSource.getUser(userId)?.toDomain()
    }

    override suspend fun updateSubscription(
        userId: String,
        subscriptionType: String,
        startDate: Date?
    ) {
        remoteDataSource.updateUserSubscription(
            userId, subscriptionType,
            Timestamp(startDate ?: Date()))
    }

    override suspend fun bookClass(userId: String, classId: String) {
        remoteDataSource.bookUserClass(userId, classId)
    }
}