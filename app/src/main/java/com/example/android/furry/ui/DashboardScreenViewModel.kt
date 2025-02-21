package com.example.android.furry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.furry.api.ApiService
import com.example.android.furry.domain.MyFriend
import com.example.android.furry.api.RetrofitInstance
import com.example.android.furry.domain.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DashboardScreenViewModel(private val apiService: ApiService) : ViewModel() {
    private val _friendsList = MutableStateFlow<List<MyFriend>?>(null)
    private val _favoritesList = MutableStateFlow<List<StoreItem>?>(null)
    val friendsList: StateFlow<List<MyFriend>?> get() = _friendsList.asStateFlow()
    val favoritesList: StateFlow<List<StoreItem>?> get() = _favoritesList.asStateFlow()

    init {
        getMyFriends()
        getMyFavorites()
    }

    private fun getMyFriends() {
        viewModelScope.launch {
            try {
                val response = apiService.getMyFriends()
                if (response.isNotEmpty()) {
                    _friendsList.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }

    private fun getMyFavorites() {
        viewModelScope.launch {
            try {
                val response = apiService.getMyFavoriteItems()
                if (response.isNotEmpty()) {
                    _favoritesList.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val apiService = RetrofitInstance.api
                DashboardScreenViewModel(
                    apiService
                )
            }
        }
    }
}
