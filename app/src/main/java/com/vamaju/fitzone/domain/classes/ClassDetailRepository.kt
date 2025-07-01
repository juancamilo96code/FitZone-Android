package com.vamaju.fitzone.domain.classes

import com.vamaju.fitzone.domain.classes.model.ClassModel
import com.vamaju.fitzone.domain.classes.model.ClassType
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface ClassDetailRepository {
    suspend fun getClassTypeById(classTypeId: String): ClassType?
    fun getFilteredClasses(
        classTypeId: String,
        dateFilter: Date?,
        cityFilter: String?
    ): Flow<List<ClassModel>>
}