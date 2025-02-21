package com.example.android.furry.domain

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