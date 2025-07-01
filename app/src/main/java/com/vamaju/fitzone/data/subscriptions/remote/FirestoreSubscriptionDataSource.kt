package com.vamaju.fitzone.data.subscriptions.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.vamaju.fitzone.data.subscriptions.model.SubscriptionTypeDto
import kotlinx.coroutines.tasks.await

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
class FirestoreSubscriptionDataSource(
    private val firestore: FirebaseFirestore
) : SubscritionDataSource {

    override suspend fun getSubscriptionTypes(): List<SubscriptionTypeDto> {
        return firestore.collection("Subscription").get()
            .await().documents.mapNotNull { doc ->
                doc.toObject(SubscriptionTypeDto::class.java)?.copy(id = doc.id)
            }
    }

}