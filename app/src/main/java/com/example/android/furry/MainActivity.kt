package com.example.android.furry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.android.furry.ui.theme.FurryTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MyFriendsViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            FurryTheme {
                Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
                    TopAppBar(
                        title = {
                            Image(
                                painter = painterResource(id = R.drawable.header_logo),
                                contentDescription = "Furry",
                                modifier = Modifier.width(110.dp)
                            )
                        },
                        actions = {
                            IconButton(onClick = { /* do something */ }) {
                                Icon(
                                    Icons.Filled.ShoppingCart,
                                    contentDescription = "Localized description"
                                )
                            }
                        })
                }) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        SectionHeader("Welcome back!", Modifier.padding(8.dp))
                        StoreAreaButtonList()
                        SectionHeader(
                            "What furry friend brought you here today?",
                            Modifier.padding(8.dp)
                        )
                        FurryFriendList()
                        SectionHeader("Your favorites", Modifier.padding(8.dp))
                        Image(
                            painter = painterResource(id = R.drawable.pet_insurance),
                            contentDescription = "AD for pet insurance",

                            )
                        SectionHeader("Your friends", Modifier.padding(8.dp))
                        MyFriendsList(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun FurryFriendList() {
    val scrollState = rememberScrollState()

    val animals = mapOf(
        "Dogs" to R.drawable.dog,
        "Cats" to R.drawable.cat,
        "Hamsters" to R.drawable.hamster,
        "Fish" to R.drawable.fish,
        "Ferrets" to R.drawable.ferret,
        "Snakes" to R.drawable.snake
    )

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(8.dp)
    ) {
        animals.forEach { (name, imageRes) ->
            Column(
                modifier = Modifier.padding(end = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = name,
                    modifier = Modifier
                        .size(92.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                )
                Text(
                    text = name,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}

@Composable
fun StoreAreaButtonList() {
    val scrollState = rememberScrollState()

    val buttonLabels = listOf(
        "Shop",
        "Today's Specials",
        "Grooming",
        "Locations",
        "Rescue",
        "Vet Finder"
    )

    Row(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .padding(8.dp)
    ) {
        buttonLabels.forEach { label ->
            OutlinedButton(
                onClick = { /* Handle button click */ },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Text(text = label)
            }
        }
    }
}

@Composable
fun SectionHeader(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FurryTheme {
        SectionHeader("Android")
    }
}