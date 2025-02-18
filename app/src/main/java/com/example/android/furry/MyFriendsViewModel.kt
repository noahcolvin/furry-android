package com.example.android.furry

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.furry.api.MyFriend
import com.example.android.furry.api.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MyFriendsViewModel : ViewModel() {
    private val apiService = RetrofitInstance.api
    val friends: MutableStateFlow<List<MyFriend>> = MutableStateFlow(emptyList())

    fun getMyFriends() {
        viewModelScope.launch {
            try {
                val response = apiService.getMyFriends()
                if (response.isNotEmpty()) {
                    friends.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
            }
        }
    }
}