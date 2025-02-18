package com.example.android.furry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.furry.api.MyFriend
import com.example.android.furry.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyFriendsViewModel : ViewModel() {
    private val _apiService = RetrofitInstance.api

    private val _friendsList = MutableStateFlow<List<MyFriend>?>(null)
    val friendsList: StateFlow<List<MyFriend>?> get() = _friendsList.asStateFlow()

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
}