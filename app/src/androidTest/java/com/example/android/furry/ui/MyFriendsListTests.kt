package com.example.android.furry.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.android.furry.domain.MyFriend
import com.example.android.furry.ui.theme.FurryTheme
import org.junit.Rule
import org.junit.Test

class MyFriendsListTests {
    private val myFriends = listOf(
        MyFriend(
            name = "My Friend",
            image = "image",
        ),
        MyFriend(
            name = "My Friend 2",
            image = "image2",
        )
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysMyFriends() {
        composeTestRule.setContent {
            FurryTheme {
                MyFriendsList(myFriends, onFriendClicked = {})
            }
        }

        composeTestRule.onNodeWithText(myFriends[0].name).assertIsDisplayed()
        composeTestRule.onNodeWithText(myFriends[1].name).assertIsDisplayed()
    }

    @Test
    fun doesNotDisplayMyFriendsIfNull() {
        composeTestRule.setContent {
            FurryTheme {
                MyFriendsList(null, onFriendClicked = {})
            }
        }

        composeTestRule.onNodeWithText("Sorry, no saved friends currently").assertIsDisplayed()
    }

    @Test
    fun doesNotDisplayMyFriendsIfEmpty() {
        composeTestRule.setContent {
            FurryTheme {
                MyFriendsList(listOf(), onFriendClicked = {})
            }
        }

        composeTestRule.onNodeWithText("Sorry, no saved friends currently").assertIsDisplayed()
    }

    @Test
    fun sendsMyFriendOnClick() {
        var clickedFriend: MyFriend? = null
        composeTestRule.setContent {
            FurryTheme {
                MyFriendsList(myFriends, onFriendClicked = { clickedFriend = it })
            }
        }

        composeTestRule.onNodeWithText(myFriends[0].name).performClick()
        assert(clickedFriend == myFriends[0])
    }
}