package com.vamaju.fitzone.domain.classes

import com.vamaju.fitzone.domain.classes.model.ClassType
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface ClassTypeRepository {
    fun getClassTypes(): Flow<List<ClassType>>
}