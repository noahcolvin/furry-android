package com.example.android.furry.api

import com.example.android.furry.domain.MyFriend
import com.example.android.furry.domain.StoreItem
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

