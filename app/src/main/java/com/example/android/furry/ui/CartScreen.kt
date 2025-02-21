package com.example.android.furry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.android.furry.api.StoreItem
import com.example.android.furry.util.formatPrice
import java.util.SortedMap

const val shipping = 4.95
const val taxRate = 7.25

@Composable
fun CartScreen(
    cartItems: List<StoreItem>,
    modifier: Modifier = Modifier,
    onAddItem: (StoreItem) -> Unit,
    onRemoveItem: (StoreItem) -> Unit,
    onCompleteOrder: () -> Unit
) {
    val groupedItems = cartItems.groupingBy { it }.eachCount().toSortedMap(compareBy { it.name })

    Column(modifier = modifier) {
        Text(
            text = "Cart",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(groupedItems.entries.toList()) { (storeItem, count) ->
                CartItem(storeItem, count, { onAddItem(storeItem) }, { onRemoveItem(storeItem) })
            }
        }
        CartSummary(groupedItems)
        Button(
            onClick = onCompleteOrder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            Text(text = "Complete Order")
        }
    }
}

@Composable
fun CartItem(storeItem: StoreItem, count: Int, onAdd: () -> Unit, onRemove: () -> Unit) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        AsyncImage(
            model = storeItem.image,
            contentDescription = storeItem.name,
            onError = { error -> println("Error loading image: ${error.result.throwable.message}") },
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(10.dp)
                )
                .width(68.dp)
        )
        Column(
            modifier = Modifier
                .padding(start = 8.dp)
                .weight(1f)
        ) {
            Text(
                text = storeItem.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                overflow = TextOverflow.Ellipsis,
            )
            Row {
                Text(
                    text = "  -  ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { onRemove() }
                )
                Text(
                    text = count.toString(),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = "  +  ",
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .clickable { onAdd() }
                )
            }
        }
        Text(
            text = formatPrice(storeItem.price * count),
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
        )
    }
}

@Composable
fun CartSummary(groupedItems: SortedMap<StoreItem, Int>) {
    val totalAmount = groupedItems.entries.sumOf { (storeItem, count) -> storeItem.price * count }
    val tax = totalAmount * taxRate / 100;

    Column {
        HorizontalDivider(
            color = Color.LightGray,
            thickness = 3.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Order Details",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            Text(
                text = "Item total",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = formatPrice(totalAmount),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            Text(
                text = "Shipping",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = formatPrice(shipping),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            Text(
                text = "Tax",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = formatPrice(tax),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            Text(
                text = "Order total",
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = formatPrice(totalAmount + shipping + tax),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}