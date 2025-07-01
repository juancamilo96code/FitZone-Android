package com.vamaju.fitzone.data.user.remote

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
interface UserAuthDataSource {
    fun getCurrentUserId(): String?
}