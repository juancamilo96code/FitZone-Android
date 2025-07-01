package com.vamaju.fitzone.di.classes

import com.vamaju.fitzone.data.classes.remote.ClassDetailDataSource
import com.vamaju.fitzone.data.classes.repository.ClassDetailRepositoryImpl
import com.vamaju.fitzone.data.user.remote.UserAuthDataSource
import com.vamaju.fitzone.data.user.remote.UserDataSource
import com.vamaju.fitzone.domain.classes.ClassDetailRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
@Module
@InstallIn(SingletonComponent::class)
object ClassRepositoryModule {

    @Provides
    @Singleton
    fun provideClassDetailRepository(
        remoteDataSource: ClassDetailDataSource,
        userDataSource: UserDataSource,
        userAuthDataSource: UserAuthDataSource
    ): ClassDetailRepository {
        return ClassDetailRepositoryImpl(
            remoteDataSource = remoteDataSource,
            userDataSource = userDataSource,
            userAuthDataSource = userAuthDataSource
        )
    }
}