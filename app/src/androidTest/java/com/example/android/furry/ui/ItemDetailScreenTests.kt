package com.example.android.furry.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.android.furry.ui.theme.FurryTheme
import org.junit.Rule
import org.junit.Test
import com.example.android.furry.domain.StoreItem

class ItemDetailScreenTests {
    private val storeItem = StoreItem(
        1,
        "My Item",
        9.99,
        "description",
        1.0,
        "image",
        listOf("one", "two"),
        listOf()
    )

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displaysStoreItem() {
        composeTestRule.setContent {
            FurryTheme {
                ItemDetailScreen(storeItem, onAddToCart = {})
            }
        }

        composeTestRule.onNodeWithText(storeItem.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(storeItem.description).assertIsDisplayed()
        composeTestRule.onNodeWithText("$${storeItem.price}").assertIsDisplayed()
        composeTestRule.onNodeWithText("• ${storeItem.about[0]}").assertIsDisplayed()
        composeTestRule.onNodeWithText("• ${storeItem.about[1]}").assertIsDisplayed()
    }

    @Test
    fun doesNotDisplayAboutIfEmpty() {
        val storeItem = StoreItem(
            1,
            "My Item",
            9.99,
            "description",
            1.0,
            "image",
            listOf(),
            listOf()
        )

        composeTestRule.setContent {
            FurryTheme {
                ItemDetailScreen(storeItem, onAddToCart = {})
            }
        }

        composeTestRule.onNodeWithText("About").assertDoesNotExist()
    }

    @Test
    fun addsItemToCart() {
        var addedItem: StoreItem? = null

        composeTestRule.setContent {
            FurryTheme {
                ItemDetailScreen(storeItem, onAddToCart = { item -> addedItem = item })
            }
        }

        composeTestRule.onNodeWithText("Add to Cart").performClick()

        assert(addedItem == storeItem)
    }
}