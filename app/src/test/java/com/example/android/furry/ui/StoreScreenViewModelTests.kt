package com.example.android.furry.ui

import com.example.android.furry.MainCoroutineRule
import com.example.android.furry.api.ApiService
import com.example.android.furry.domain.StoreItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class StoreScreenViewModelTests {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var viewModel: StoreScreenViewModel
    private val storeItems = listOf(
        StoreItem(
            1,
            "item1",
            1.0,
            "description",
            1.0,
            "image",
            listOf(),
            listOf()
        )
    )

    @Before
    fun setUp() {
        mockApiService = mock<ApiService> {
            onBlocking { getStoreItems(null, null, null) }.doReturn(storeItems)
        }
        viewModel = StoreScreenViewModel(mockApiService)
    }

    @Test
    fun onSearchTextChanged_updatesSearchText() {
        val searchText = "test"
        viewModel.onSearchTextChanged(searchText)

        assertEquals(searchText, viewModel.searchText.value)
    }

    @Test
    fun onSearchButtonClicked_updatesSearchText() {
        viewModel.onSearchChanged(true)

        assertEquals(true, viewModel.isSearching.value)
    }

    @Test
    fun getStoreItemsList_nullParams_setsStoreItems() = runTest {
        viewModel.getStoreItemsList()

        assertEquals(storeItems, viewModel.storeItemsList.value)
    }

    @Test
    fun getStoreItemsList_allAnimalProduct_setsStoreItems() = runTest {
        viewModel.getStoreItemsList("All", "All")

        assertEquals(storeItems, viewModel.storeItemsList.value)
    }

    @Test
    fun getStoreItemsList_withAnimalProductSearch_setsStoreItems() = runTest {
        mockApiService = mock<ApiService> {
            onBlocking { getStoreItems("Dog", "Toy", "Good") }.doReturn(storeItems)
        }
        viewModel = StoreScreenViewModel(mockApiService)

        viewModel.getStoreItemsList("Dog", "Toy", "Good")

        assertEquals(storeItems, viewModel.storeItemsList.value)
    }

    @Test
    fun getStoreItemsList_exception_setsEmptyStoreItems() = runTest {
        whenever(
            mockApiService.getStoreItems("Dog", "Toy", "Good")
        ).thenThrow(RuntimeException::class.java)
        viewModel = StoreScreenViewModel(mockApiService)

        viewModel.getStoreItemsList("Dog", "Toy", "Good")

        assertTrue(viewModel.storeItemsList.value?.isEmpty() ?: false)
    }
}

