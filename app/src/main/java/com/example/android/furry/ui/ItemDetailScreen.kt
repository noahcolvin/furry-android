package com.example.android.furry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.android.furry.api.MyFriend
import com.example.android.furry.api.StoreItem
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun ItemDetailScreen(storeItem: StoreItem, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = storeItem.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        RatingBar(
            value = storeItem.rating.toFloat(),
            style = RatingBarStyle.Fill(),
            size = 14.dp,
            spaceBetween = 1.dp,
            modifier = Modifier.padding(start = 8.dp, top = 0.dp, bottom = 8.dp),
            onValueChange = { },
            onRatingChanged = { }
        )
        AsyncImage(
            model = storeItem.image,
            contentDescription = storeItem.name,
            onError = { error -> println("Error loading image: ${error.result.throwable.message}") },
            contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(10.dp)
                )
        )
        Text(
            text = "$${storeItem.price}",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = storeItem.description,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(8.dp)
        )
        Button(
            onClick = { /* Handle add to cart action */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Add to Cart", color = Color.White)
        }
        Text(
            text = "About",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        storeItem.about.forEach { about ->
            Text(
                text = "• $about",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, end = 8.dp)
            )
        }
    }
}