package com.vamaju.fitzone.di.classes

import com.vamaju.fitzone.data.classes.remote.FirestoreClassTypeDataSource
import com.vamaju.fitzone.data.classes.remote.FirestoreClassTypeDataSourceImpl
import com.vamaju.fitzone.data.classes.repository.ClassTypeRepositoryImpl
import com.vamaju.fitzone.domain.classes.ClassTypeRepository
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
abstract class ClassModule {

    @Binds
    @Singleton
    abstract fun bindFirestoreClassTypeDataSource(
        firestoreClassTypeDataSourceImpl: FirestoreClassTypeDataSourceImpl
    ): FirestoreClassTypeDataSource

    @Binds
    @Singleton
    abstract fun bindClassTypeRepository(
        classTypeRepositoryImpl: ClassTypeRepositoryImpl
    ): ClassTypeRepository
}