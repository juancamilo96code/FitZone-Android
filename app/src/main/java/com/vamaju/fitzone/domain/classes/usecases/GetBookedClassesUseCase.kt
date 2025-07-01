package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.classes.model.GroupedClasses
import java.util.Date
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class GetBookedClassesUseCase @Inject constructor(
    private val classDetailRepository: ClassDetailRepository
) {
    suspend operator fun invoke(): GroupedClasses {
        val allBookedClasses = classDetailRepository.getBookedClasses()
        val now = Date()

        val upcomingClasses = allBookedClasses
            .filter { it.dateTime.toDate().after(now) }
            .sortedBy { it.dateTime } // Ordenar por fecha, las más próximas primero

        val pastClasses = allBookedClasses
            .filter { it.dateTime.toDate().before(now) || it.dateTime.equals(now) } // Incluye clases que ya empezaron o terminaron
            .sortedByDescending { it.dateTime } // Ordenar por fecha descendente, las más recientes primero

        return GroupedClasses(
            upcoming = upcomingClasses,
            past = pastClasses
        )
    }
}