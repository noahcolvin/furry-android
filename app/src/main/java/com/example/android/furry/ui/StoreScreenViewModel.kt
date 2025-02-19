package com.example.android.furry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.furry.api.RetrofitInstance
import com.example.android.furry.api.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreScreenViewModel : ViewModel() {
    private val _apiService = RetrofitInstance.api

    private val _storeItemsList = MutableStateFlow<List<StoreItem>?>(null)
    val storeItemsList: StateFlow<List<StoreItem>?> get() = _storeItemsList.asStateFlow()

    init {
        getStoreItemsList()
    }

    private fun getStoreItemsList() {
        viewModelScope.launch {
            try {
                val response = _apiService.getStoreItems()
                if (response.isNotEmpty()) {
                    _storeItemsList.value = response
                }
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }
}