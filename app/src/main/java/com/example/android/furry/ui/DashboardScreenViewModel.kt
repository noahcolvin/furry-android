package com.example.android.furry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.furry.api.MyFriend
import com.example.android.furry.api.RetrofitInstance
import com.example.android.furry.api.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardScreenViewModel : ViewModel() {
    private val _apiService = RetrofitInstance.api

    private val _friendsList = MutableStateFlow<List<MyFriend>?>(null)
    private val _favoritesList = MutableStateFlow<List<StoreItem>?>(null)
    val friendsList: StateFlow<List<MyFriend>?> get() = _friendsList.asStateFlow()
    val favoritesList: StateFlow<List<StoreItem>?> get() = _favoritesList.asStateFlow()

    fun getMyFriends() {
        viewModelScope.launch {
            try {
                val response = _apiService.getMyFriends()
                if (response.isNotEmpty()) {
                    _friendsList.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }

    fun getMyFavorites() {
        viewModelScope.launch {
            try {
                val response = _apiService.getMyFavoriteItems()
                if (response.isNotEmpty()) {
                    _favoritesList.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }
}
