package com.example.android.furry.api

import retrofit2.http.GET

interface ApiService {
    @GET("my-friends")
    suspend fun getMyFriends(): List<MyFriend>
}

data class MyFriend(
    val name: String,
    val image: String
)
