package com.vamaju.fitzone.di.classes

import com.vamaju.fitzone.data.classes.remote.ClassDetailDataSource
import com.vamaju.fitzone.data.classes.remote.ClassTypeDataSource
import com.vamaju.fitzone.data.classes.remote.FirestoreClassDetailDataSource
import com.vamaju.fitzone.data.classes.remote.FirestoreClassTypeDataSourceImpl
import com.vamaju.fitzone.data.classes.repository.ClassDetailRepositoryImpl
import com.vamaju.fitzone.data.classes.repository.ClassTypeRepositoryImpl
import com.vamaju.fitzone.domain.classes.ClassDetailRepository
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
    ): ClassTypeDataSource

    @Binds
    @Singleton
    abstract fun bindClassTypeRepository(
        classTypeRepositoryImpl: ClassTypeRepositoryImpl
    ): ClassTypeRepository

    // ... Tus binds existentes para ClassDataSource y ClassRepository
    @Binds
    @Singleton
    abstract fun bindClassDetailDataSource(
        firestoreClassDetailDataSource: FirestoreClassDetailDataSource
    ): ClassDetailDataSource

    @Binds
    @Singleton
    abstract fun bindClassDetailRepository(
        classDetailRepositoryImpl: ClassDetailRepositoryImpl
    ): ClassDetailRepository

}