package com.example.android.furry.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.furry.api.StoreItem

@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    viewModel: StoreScreenViewModel = viewModel(),
    onStoreItemClicked: (StoreItem) -> Unit
) {
    val storeItems by viewModel.storeItemsList.collectAsState()

    if (!storeItems.isNullOrEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = modifier
        ) {
            items(storeItems!!.size) { index ->
                StoreItem(
                    item = storeItems!![index],
                    modifier = Modifier
                        .padding(8.dp)
                        .clickable { onStoreItemClicked(storeItems!![index]) })
            }
        }
    }
}