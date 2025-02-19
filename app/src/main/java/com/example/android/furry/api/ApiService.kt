package com.example.android.furry.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("my-friends")
    suspend fun getMyFriends(): List<MyFriend>

    @GET("my-favorite-items")
    suspend fun getMyFavoriteItems(): List<StoreItem>

    @GET("items")
    suspend fun getStoreItems(
        @Query("animal") animal: String?,
        @Query("product") product: String?,
        @Query("search") search: String?,
    ): List<StoreItem>
}

data class StoreItem(
    val id: Int,
    val name: String,
    val price: Double,
    val description: String,
    val rating: Double,
    val image: String,
    val about: List<String>,
    val categories: List<String>
)

data class MyFriend(
    val name: String,
    val image: String
)
