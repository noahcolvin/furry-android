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

    fun getStoreItemsList(
        animal: String? = null,
        product: String? = null,
        search: String? = null
    ) {
        viewModelScope.launch {
            try {
                val animalParam = if (animal == "All") null else animal
                val productParam = if (product == "All") null else product

                val response = _apiService.getStoreItems(animalParam, productParam, search)
                _storeItemsList.value = response
            } catch (e: Exception) {
                // Handle errors here
                val x = e
            }
        }
    }
}