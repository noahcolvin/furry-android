package com.example.android.furry

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MyFriendsList(viewModel: MyFriendsViewModel) {
    val friends by viewModel.friends.collectAsState()
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(8.dp)
    ) {
        friends.forEach { label ->
            OutlinedButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = label.name)
            }
        }
    }
    DisposableEffect(Unit) {
        viewModel.getMyFriends()
        onDispose {}
    }
}