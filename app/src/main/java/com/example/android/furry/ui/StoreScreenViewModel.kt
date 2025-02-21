package com.example.android.furry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.android.furry.api.ApiService
import com.example.android.furry.api.RetrofitInstance
import com.example.android.furry.api.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StoreScreenViewModel(private val apiService: ApiService) : ViewModel() {
    private val _storeItemsList = MutableStateFlow<List<StoreItem>?>(null)
    val storeItemsList: StateFlow<List<StoreItem>?> get() = _storeItemsList.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText: StateFlow<String> get() = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching: StateFlow<Boolean> get() = _isSearching.asStateFlow()

    fun onSearchTextChanged(text: String) {
        _searchText.value = text
        getStoreItemsList(search = text)
    }

    fun onSearchChanged(searching: Boolean) {
        _isSearching.value = searching
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

                val response = apiService.getStoreItems(animalParam, productParam, search)
                _storeItemsList.value = response
            } catch (e: Exception) {
                _storeItemsList.value = listOf()
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val apiService = RetrofitInstance.api
                StoreScreenViewModel(
                    apiService
                )
            }
        }
    }
}