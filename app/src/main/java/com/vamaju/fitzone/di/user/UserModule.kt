package com.vamaju.fitzone.di.user

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.user.remote.FirebaseAuthUserRepository
import com.vamaju.fitzone.data.user.remote.FirestoreUserDataSource
import com.vamaju.fitzone.data.user.remote.UserAuthDataSource
import com.vamaju.fitzone.data.user.remote.UserDataSource
import com.vamaju.fitzone.data.user.repository.UserRepositoryImpl
import com.vamaju.fitzone.domain.user.UserRepository
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
object UserModule {

    @Provides
    @Singleton
    fun provideUserAuthDataSource(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): UserAuthDataSource {
        return FirebaseAuthUserRepository(
            auth = auth,
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        firestore: FirebaseFirestore
    ): UserDataSource {
        return FirestoreUserDataSource(
            firestore = firestore
        )
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        remoteDataSource: UserDataSource,
        userAuthDataSource: UserAuthDataSource
    ): UserRepository {
        return UserRepositoryImpl(
            remoteDataSource = remoteDataSource,
            userAuthDataSource = userAuthDataSource
        )
    }
}