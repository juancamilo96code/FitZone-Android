package com.vamaju.fitzone.domain.locations

import com.vamaju.fitzone.domain.locations.model.Location
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface LocationRepository {
    fun getLocations(): Flow<List<Location>>
}