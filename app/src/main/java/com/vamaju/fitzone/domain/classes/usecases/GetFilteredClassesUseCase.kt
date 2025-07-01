package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.classes.model.ClassModel
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class GetFilteredClassesUseCase @Inject constructor(
    private val repository: ClassDetailRepository
) {
    operator fun invoke(
        classTypeId: String,
        dateFilter: Date?,
        cityFilter: String?
    ): Flow<List<ClassModel>> {
        return repository.getFilteredClasses(classTypeId, dateFilter, cityFilter)
    }
}