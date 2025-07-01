package com.vamaju.fitzone.data.locations.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.locations.model.LocationDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Singleton
class FirestoreLocationDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : LocationDataSource {

    override fun getLocations(): Flow<List<LocationDto>> = callbackFlow {
        val subscription = firestore.collection("Location")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    close(exception)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val locations = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(LocationDto::class.java)?.copy(id = doc.id)
                    }
                    trySend(locations).isSuccess
                }
            }
        awaitClose { subscription.remove() }
    }

}