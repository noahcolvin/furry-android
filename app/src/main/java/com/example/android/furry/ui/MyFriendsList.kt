package com.example.android.furry.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.android.furry.api.MyFriend

@Composable
fun MyFriendsList(friends: List<MyFriend>?, onFriendClicked: (MyFriend) -> Unit) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(8.dp)
    ) {
        friends?.forEach { friend ->
            Column(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .clickable { onFriendClicked(friend) },
                horizontalAlignment = Alignment.Start
            ) {
                AsyncImage(
                    model = friend.image,
                    contentDescription = friend.name,
                    onError = { error -> println("Error loading image: ${error.result.throwable.message}") },
                    contentScale = androidx.compose.ui.layout.ContentScale.FillWidth,
                    modifier = Modifier
                        .width(200.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .border(
                            1.dp,
                            Color.LightGray,
                            RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                        )
                )
                Text(
                    text = friend.name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MyFriendsListPreview() {
    val sampleFriends = listOf(
        MyFriend(name = "Buddy", image = "https://picsum.photos/id/237/200/300"),
        MyFriend(name = "Max", image = "https://picsum.photos/id/237/200/300"),
        MyFriend(name = "Bella", image = "https://picsum.photos/id/237/200/300")
    )
    MyFriendsList(friends = sampleFriends, onFriendClicked = {})
}