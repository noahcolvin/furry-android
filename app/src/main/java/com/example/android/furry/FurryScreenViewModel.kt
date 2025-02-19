package com.example.android.furry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.furry.api.MyFriend
import com.example.android.furry.api.RetrofitInstance
import com.example.android.furry.api.StoreItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FurryScreenViewModel : ViewModel() {
    private var _selectedFriend: MyFriend? = null
    val selectedFriend: MyFriend? get() = _selectedFriend

    private var _selectedStoreItem: StoreItem? = null
    val selectedStoreItem: StoreItem? get() = _selectedStoreItem

    fun setSelectedFriend(friend: MyFriend) {
        _selectedFriend = friend
    }

    fun setSelectedStoreItem(storeItem: StoreItem) {
        _selectedStoreItem = storeItem
    }
}