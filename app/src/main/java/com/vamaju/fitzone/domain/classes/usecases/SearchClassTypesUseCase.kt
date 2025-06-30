package com.vamaju.fitzone.domain.classes.usecases

import com.vamaju.fitzone.domain.classes.ClassTypeRepository
import com.vamaju.fitzone.domain.classes.model.ClassType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class SearchClassTypesUseCase @Inject constructor(
    private val repository: ClassTypeRepository
) {
    operator fun invoke(query: String): Flow<List<ClassType>> {
        return repository.getClassTypes().map { classes ->
            if (query.isBlank()) {
                classes
            } else {
                classes.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }
    }
}