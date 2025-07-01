package com.vamaju.fitzone.data.classes.repository

import com.vamaju.fitzone.data.classes.remote.ClassTypeDataSource
import com.vamaju.fitzone.domain.classes.ClassTypeRepository
import com.vamaju.fitzone.domain.classes.model.ClassType
import com.vamaju.fitzone.domain.classes.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Singleton
class ClassTypeRepositoryImpl @Inject constructor(
    private val remoteDataSource: ClassTypeDataSource,
): ClassTypeRepository {

    override fun getClassTypes(): Flow<List<ClassType>> {
        return remoteDataSource.getClassTypes().map { dtos ->
            dtos.map { it.toDomain() }
        }
    }
}