package com.example.android.furry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.android.furry.api.StoreItem
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle

@Composable
fun StoreItem(item: StoreItem, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
            .width(175.dp),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = item.image,
            contentDescription = item.name,
            onError = { error -> println("Error loading image: ${error.result.throwable.message}") },
            contentScale = ContentScale.FillWidth,
            modifier = Modifier

                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .border(
                    1.dp,
                    Color.LightGray,
                    RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                )
        )
        Text(
            text = item.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        Text(
            text = item.description,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal,
            lineHeight = 16.sp,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp).height(48.dp)
        )
        Text(
            text = "$${item.price}",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 8.dp, top = 8.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            RatingBar(
                value = item.rating.toFloat(),
                style = RatingBarStyle.Fill(),
                size = 14.dp,
                spaceBetween = 1.dp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp),
                onValueChange = { },
                onRatingChanged = { }
            )
            Text(
                text = "(${item.rating})",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(start = 2.dp, top = 2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreItemPreview() {
    val sampleStoreItem = StoreItem(
        id = 1,
        name = "Dog Food",
        price = 19.99,
        description = "A friendly dog",
        rating = 4.5,
        image = "https://picsum.photos/id/237/200/300",
        about = listOf("Loyal", "Playful", "Energetic"),
        categories = listOf("Dog", "Pet")
    )

    StoreItem(item = sampleStoreItem)
}