package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassTypeRepository
import com.vamaju.fitzone.domain.classes.model.ClassType
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class GetClassTypeDetailsUseCase @Inject constructor(
    private val repository: ClassTypeRepository
) {
    suspend operator fun invoke(classTypeId: String): ClassType? {
        return repository.getClassTypeById(classTypeId)
    }
}