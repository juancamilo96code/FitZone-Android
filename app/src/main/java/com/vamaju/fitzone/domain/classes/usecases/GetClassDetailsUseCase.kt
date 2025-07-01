package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.classes.model.ClassModel
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class GetClassDetailsUseCase @Inject constructor(
    private val classDetailRepository: ClassDetailRepository
) {
    suspend operator fun invoke(classId: String): ClassModel? {
        return classDetailRepository.getClassById(classId)
    }
}
