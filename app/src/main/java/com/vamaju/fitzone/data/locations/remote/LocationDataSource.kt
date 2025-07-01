package com.vamaju.fitzone.data.locations.remote

import com.vamaju.fitzone.data.locations.model.LocationDto
import kotlinx.coroutines.flow.Flow

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
interface LocationDataSource {
    fun getLocations(): Flow<List<LocationDto>>
}