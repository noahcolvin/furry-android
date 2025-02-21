package com.example.android.furry

import androidx.lifecycle.ViewModel
import com.example.android.furry.domain.MyFriend
import com.example.android.furry.domain.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FurryScreenViewModel : ViewModel() {
    private var _selectedFriend: MyFriend? = null
    val selectedFriend: MyFriend? get() = _selectedFriend

    private var _selectedStoreItem: StoreItem? = null
    val selectedStoreItem: StoreItem? get() = _selectedStoreItem

    private var _storeAnimalFilter: String? = null
    val storeAnimalFilter: String? get() = _storeAnimalFilter

    private var _cartItems = MutableStateFlow<List<StoreItem>>(emptyList())
    val cartItems: StateFlow<List<StoreItem>> get() = _cartItems.asStateFlow()

    fun addToCart(storeItem: StoreItem) {
        _cartItems.value += storeItem
    }

    fun removeFromCart(storeItem: StoreItem) {
        _cartItems.value -= storeItem
    }

    fun completeOrder() {
        _cartItems.value = emptyList()
    }

    fun setStoreAnimalFilter(animal: String) {
        _storeAnimalFilter = animal
    }

    fun setSelectedFriend(friend: MyFriend) {
        _selectedFriend = friend
    }

    fun setSelectedStoreItem(storeItem: StoreItem) {
        _selectedStoreItem = storeItem
    }
}