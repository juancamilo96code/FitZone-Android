package com.vamaju.fitzone.data.locations.repository

import com.vamaju.fitzone.data.locations.remote.LocationDataSource
import com.vamaju.fitzone.domain.locations.LocationRepository
import com.vamaju.fitzone.domain.locations.model.Location
import com.vamaju.fitzone.domain.locations.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
class LocationRepositoryImpl @Inject constructor(
    private val remoteDataSource: LocationDataSource
) : LocationRepository {

    override fun getLocations(): Flow<List<Location>> {
        return remoteDataSource.getLocations().map { locations ->
            locations.map { it.toDomain() }
        }
    }

}