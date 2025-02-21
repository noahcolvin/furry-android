package com.example.android.furry.ui

import com.example.android.furry.MainCoroutineRule
import com.example.android.furry.api.ApiService
import com.example.android.furry.domain.MyFriend
import com.example.android.furry.domain.StoreItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class DashboardScreenViewModelTests {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var mockApiService: ApiService

    private lateinit var viewModel: DashboardScreenViewModel
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
    private val myFriends = listOf(
        MyFriend(
            "friend",
            "image",
        )
    )

    @Before
    fun setUp() {
        mockApiService = mock<ApiService> {
            onBlocking { getMyFavoriteItems() }.doReturn(storeItems)
            onBlocking { getMyFriends() }.doReturn(myFriends)
        }
        viewModel = DashboardScreenViewModel(mockApiService)
    }

    @Test
    fun `get my friends`() = runTest {
        // init loads these
        assert(viewModel.friendsList.value == myFriends)
    }

    @Test
    fun `get my favorites`() = runTest {
        // init loads these
        assert(viewModel.favoritesList.value == storeItems)
    }
}