package com.example.android.furry.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android.furry.domain.StoreItem

@Composable
fun MyFavoriteItemsList(storeItems: List<StoreItem>?, onStoreItemClicked: (StoreItem) -> Unit) {
    val scrollState = rememberScrollState()

    if (storeItems.isNullOrEmpty()) {
        Text(
            text = "Sorry, no items available",
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
    } else {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .horizontalScroll(scrollState)
                .padding(8.dp)
        ) {
            storeItems.forEach { storeItem ->
                StoreItem(storeItem, Modifier.clickable { onStoreItemClicked(storeItem) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyFavoriteItemsListPreview() {
    val sampleStoreItems = listOf(
        StoreItem(
            id = 1,
            name = "Dog Food",
            price = 19.99,
            description = "A friendly dog",
            rating = 4.5,
            image = "https://picsum.photos/id/237/200/300",
            about = listOf("Loyal", "Playful", "Energetic"),
            categories = listOf("Dog", "Pet")
        ),
        StoreItem(
            id = 2,
            name = "Cat Toy",
            price = 29.99,
            description = "A cute cat",
            rating = 4.7,
            image = "https://picsum.photos/id/238/200/300",
            about = listOf("Independent", "Curious", "Affectionate"),
            categories = listOf("Cat", "Pet")
        ),
        StoreItem(
            id = 3,
            name = "Hamster Wheel",
            price = 15.99,
            description = "A lovely hamster",
            rating = 4.3,
            image = "https://picsum.photos/id/239/200/300",
            about = listOf("Small", "Active", "Friendly"),
            categories = listOf("Hamster", "Pet")
        )
    )
    MyFavoriteItemsList(storeItems = sampleStoreItems, onStoreItemClicked = {})
}