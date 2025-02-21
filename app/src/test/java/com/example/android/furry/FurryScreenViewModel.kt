package com.example.android.furry

import com.example.android.furry.domain.MyFriend
import com.example.android.furry.domain.StoreItem
import org.junit.Before
import org.junit.Test

class FurryScreenViewModelTests {
    private lateinit var viewModel: FurryScreenViewModel

    @Before
    fun setUp() {
        viewModel = FurryScreenViewModel()
    }

    @Test
    fun `add item to cart`() {
        val item = StoreItem(
            id = 1,
            name = "Dog",
            description = "A cute dog",
            price = 100.0,
            image = "https://example.com/dog.jpg",
            rating = 4.5,
            about = listOf(),
            categories = listOf(),
        )

        viewModel.addToCart(item)
        assert(viewModel.cartItems.value.size == 1)
        assert(viewModel.cartItems.value.contains(item))
    }

    @Test
    fun `remove item from cart`() {
        val item = StoreItem(
            id = 1,
            name = "Dog",
            description = "A cute dog",
            price = 100.0,
            image = "https://example.com/dog.jpg",
            rating = 4.5,
            about = listOf(),
            categories = listOf(),
        )

        viewModel.addToCart(item)
        assert(viewModel.cartItems.value.size == 1)
        assert(viewModel.cartItems.value.contains(item))

        viewModel.removeFromCart(item)
        assert(viewModel.cartItems.value.isEmpty())
    }

    @Test
    fun `complete order`() {
        val item = StoreItem(
            id = 1,
            name = "Dog",
            description = "A cute dog",
            price = 100.0,
            image = "https://example.com/dog.jpg",
            rating = 4.5,
            about = listOf(),
            categories = listOf(),
        )

        viewModel.addToCart(item)
        assert(viewModel.cartItems.value.size == 1)
        assert(viewModel.cartItems.value.contains(item))

        viewModel.completeOrder()
        assert(viewModel.cartItems.value.isEmpty())
    }

    @Test
    fun `set store animal filter`() {
        viewModel.setStoreAnimalFilter("Dog")
        assert(viewModel.storeAnimalFilter == "Dog")
    }

    @Test
    fun `set selected friend`() {
        val friend = MyFriend(

            name = "Alice",
            image = "https://example.com/alice.jpg",
        )

        viewModel.setSelectedFriend(friend)
        assert(viewModel.selectedFriend == friend)
    }

    @Test
    fun `set selected store item`() {
        val item = StoreItem(
            id = 1,
            name = "Dog",
            description = "A cute dog",
            price = 100.0,
            image = "https://example.com/dog.jpg",
            rating = 4.5,
            about = listOf(),
            categories = listOf(),
        )

        viewModel.setSelectedStoreItem(item)
        assert(viewModel.selectedStoreItem == item)
    }
}