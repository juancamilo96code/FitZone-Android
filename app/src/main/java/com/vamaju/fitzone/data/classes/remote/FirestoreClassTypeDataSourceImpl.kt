package com.vamaju.fitzone.data.classes.remote

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.classes.model.ClassTypeDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/

@Singleton
class FirestoreClassTypeDataSourceImpl @Inject constructor(
    private val firestore: FirebaseFirestore
):ClassTypeDataSource {

    override suspend fun getClassTypeById(classTypeId: String): ClassTypeDto? {
        return try {
            val documentSnapshot = firestore.collection("ClassType")
                .document(classTypeId).get().await()
            documentSnapshot.toObject(ClassTypeDto::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            null
        }
    }

    override fun getClassTypes(): Flow<List<ClassTypeDto>> = callbackFlow{
        val collectionRef = firestore.collection("ClassType")

        val snapshotListener = collectionRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }
            Log.e("FirestoreClassTypeDataSourceImpl", "getClassTypes: ${snapshot.toString()}")

            if (snapshot != null && !snapshot.isEmpty) {
                val classTypes = snapshot.documents.mapNotNull { doc ->
                    Log.d("FirestoreClassTypeDataSourceImpl", "getClassTypes: ${doc.toString()}")
                    doc.toObject(ClassTypeDto::class.java)?.copy(id = doc.id)
                }
                trySend(classTypes).isSuccess
            } else {
                trySend(emptyList()).isSuccess
            }
        }

        awaitClose { snapshotListener.remove() }
    }
}