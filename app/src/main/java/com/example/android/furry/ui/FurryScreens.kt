package com.example.android.furry.ui

import androidx.annotation.StringRes
import com.example.android.furry.R

enum class FurryScreens(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Friend(title = R.string.friend),
    ItemDetail(title = R.string.item_detail),
    Store(title = R.string.store),
    Cart(title = R.string.cart),
}