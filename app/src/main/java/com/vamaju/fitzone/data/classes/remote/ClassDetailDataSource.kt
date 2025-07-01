package com.vamaju.fitzone.data.classes.remote

import com.vamaju.fitzone.data.classes.model.ClassDto
import com.vamaju.fitzone.data.classes.model.ClassTypeDto
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface ClassDetailDataSource {
    suspend fun getClassTypeById(classTypeId: String): ClassTypeDto?
    fun getFilteredClasses(classTypeId: String, dateFilter: Date?, cityFilter: String?): Flow<List<ClassDto>>
}