package com.vamaju.fitzone.di.location

import com.vamaju.fitzone.data.classes.remote.ClassDetailDataSource
import com.vamaju.fitzone.data.classes.remote.FirestoreClassDetailDataSource
import com.vamaju.fitzone.data.classes.repository.ClassDetailRepositoryImpl
import com.vamaju.fitzone.data.locations.remote.FirestoreLocationDataSource
import com.vamaju.fitzone.data.locations.remote.LocationDataSource
import com.vamaju.fitzone.data.locations.repository.LocationRepositoryImpl
import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import com.vamaju.fitzone.domain.locations.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {
    // ... Tus binds existentes para ClassDataSource y ClassRepository
    @Binds
    @Singleton
    abstract fun bindLocationDataSource(
        firestoreLocationDataSource: FirestoreLocationDataSource
    ): LocationDataSource

    @Binds
    @Singleton
    abstract fun bindClassDetailRepository(
        locationRepositoryImpl: LocationRepositoryImpl
    ): LocationRepository


}