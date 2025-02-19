package com.example.android.furry.api

import retrofit2.http.GET

interface ApiService {
    @GET("my-friends")
    suspend fun getMyFriends(): List<MyFriend>

    @GET("my-favorite-items")
    suspend fun getMyFavoriteItems(): List<StoreItem>
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
