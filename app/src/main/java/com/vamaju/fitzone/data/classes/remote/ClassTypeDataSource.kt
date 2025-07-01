package com.vamaju.fitzone.data.classes.remote

import com.vamaju.fitzone.data.classes.model.ClassTypeDto
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface ClassTypeDataSource {
    fun getClassTypes(): Flow<List<ClassTypeDto>>
    suspend fun getClassTypeById(classTypeId: String): ClassTypeDto?
}