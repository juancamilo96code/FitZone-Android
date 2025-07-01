package com.vamaju.fitzone.data.classes.repository

import com.vamaju.fitzone.data.classes.remote.ClassDetailDataSource
import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.classes.model.ClassModel
import com.vamaju.fitzone.domain.classes.model.ClassType
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
    private val remoteDataSource: ClassDetailDataSource
) : ClassDetailRepository {

    override suspend fun getClassTypeById(classTypeId: String): ClassType? {
        return remoteDataSource.getClassTypeById(classTypeId)?.toDomain()
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
}