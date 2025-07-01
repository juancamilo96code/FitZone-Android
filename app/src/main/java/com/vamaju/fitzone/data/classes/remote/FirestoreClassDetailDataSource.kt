package com.vamaju.fitzone.data.classes.remote

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.classes.model.ClassDto
import com.vamaju.fitzone.data.classes.model.ClassTypeDto
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Singleton
class FirestoreClassDetailDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) : ClassDetailDataSource {

    override suspend fun getClassTypeById(classTypeId: String): ClassTypeDto? {
        return try {
            val documentSnapshot = firestore.collection("ClassType")
                .document(classTypeId).get().await()
            documentSnapshot.toObject(ClassTypeDto::class.java)?.copy(id = documentSnapshot.id)
        } catch (e: Exception) {
            null
        }
    }

    override fun getFilteredClasses(
        classTypeId: String,
        dateFilter: Date?,
        cityFilter: String?
    ): Flow<List<ClassDto>> = callbackFlow {
        val classesRef = firestore.collection("Class")
            .whereEqualTo("typeClassId", classTypeId)

        var query = classesRef.orderBy("dateTime")


        if (dateFilter != null) {
            val calendar = Calendar.getInstance().apply { time = dateFilter }
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val startOfDay = Timestamp(calendar.time)
            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val startOfNextDay = Timestamp(calendar.time)

            query = query
                .whereGreaterThanOrEqualTo("dateTime", startOfDay)
                .whereLessThan("dateTime", startOfNextDay)
        }

        if (!cityFilter.isNullOrBlank()) {
            query = query.whereEqualTo("locationId", cityFilter)
        }

        val subscription = query.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                close(exception)
                return@addSnapshotListener
            }
            if (snapshot != null) {
                val classes = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(ClassDto::class.java)?.copy(id = doc.id)
                }
                trySend(classes).isSuccess
            }
        }
        awaitClose { subscription.remove() }
    }
}