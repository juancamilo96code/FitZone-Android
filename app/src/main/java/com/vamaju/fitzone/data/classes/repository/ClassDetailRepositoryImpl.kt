package com.vamaju.fitzone.data.classes.repository

import com.vamaju.fitzone.data.classes.remote.ClassDetailDataSource
import com.vamaju.fitzone.data.user.remote.UserAuthDataSource
import com.vamaju.fitzone.data.user.remote.UserDataSource
import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.classes.model.ClassModel
import com.vamaju.fitzone.domain.classes.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/

@Singleton
class ClassDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClassDetailDataSource,
    private val userDataSource: UserDataSource,
    private val userAuthDataSource: UserAuthDataSource
) : ClassDetailRepository {

    override suspend fun getClassById(classId: String): ClassModel? {
        return remoteDataSource.getClassById(classId)?.toDomain()
    }

    override fun getFilteredClasses(
        classTypeId: String,
        dateFilter: Date?,
        cityFilter: String?
    ): Flow<List<ClassModel>> {
        return remoteDataSource
            .getFilteredClasses(classTypeId, dateFilter, cityFilter)
            .map { dtos ->
                dtos.map { it.toDomain() }
            }
    }

    override suspend fun getClassesById(classId: String): ClassModel? {
        return remoteDataSource.getClassById(classId)?.toDomain()
    }

    override suspend fun getBookedClasses(): List<ClassModel> {
        val userId = userAuthDataSource.getCurrentUserId()
            ?: throw IllegalStateException("User not logged in.")

        val user = userDataSource.getUser(userId)
        val bookedClassIds = user?.classesBooked ?: emptyList()

        // Obtener los detalles completos de las clases
        return remoteDataSource.getClassesByIds(bookedClassIds).map { it.toDomain() }
    }
}