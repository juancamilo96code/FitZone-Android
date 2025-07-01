package com.vamaju.fitzone.domain.locations.usecases

import com.vamaju.fitzone.domain.locations.LocationRepository
import com.vamaju.fitzone.domain.locations.model.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class GetLocationsUseCase @Inject constructor(
    private val repository: LocationRepository
) {
    operator fun invoke(): Flow<List<Location>> {
        return repository.getLocations()
    }
}