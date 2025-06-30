package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassTypeRepository
import com.vamaju.fitzone.domain.classes.model.ClassType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class GetClassTypesUseCase @Inject constructor(
    private val repository: ClassTypeRepository
) {
    operator fun invoke(): Flow<List<ClassType>> {
        return repository.getClassTypes()
    }
}