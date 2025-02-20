package com.example.android.furry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.android.furry.ui.CartScreen
import com.example.android.furry.ui.DashboardScreen
import com.example.android.furry.ui.FurryScreens
import com.example.android.furry.ui.ItemDetailScreen
import com.example.android.furry.ui.MyFriendScreen
import com.example.android.furry.ui.StoreScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FurryAppBar(
    currentScreen: FurryScreens,
    canNavigateBack: Boolean,
    itemCount: Int,
    navigateUp: () -> Unit,
    onCartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        actions = { ShoppingCartIcon(itemCount, onClick = onCartClick) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageAppBar(itemCount: Int, onCartClick: () -> Unit) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.header_logo),
                contentDescription = "Furry",
                modifier = Modifier.width(110.dp)
            )
        },
        actions = {
            ShoppingCartIcon(itemCount, onClick = onCartClick)
        })
}

@Composable
fun ShoppingCartIcon(itemCount: Int, onClick: () -> Unit) {
    BadgedBox(
        badge = {
            if (itemCount > 0) {
                Badge(
                    containerColor = Color.Red,
                    contentColor = Color.White,
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Text("$itemCount")
                }
            }
        }
    ) {
        IconButton(onClick) {
            Icon(
                imageVector = Icons.Filled.ShoppingCart,
                contentDescription = "Localized description",
            )
        }
    }
}

@Composable
fun FurryApp(
    viewModel: FurryScreenViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = FurryScreens.valueOf(
        backStackEntry?.destination?.route ?: FurryScreens.Start.name
    )

    val cartItems by viewModel.cartItems.collectAsState()

    Scaffold(
        topBar = {
            currentScreen.let {
                if (it == FurryScreens.Start) {
                    ImageAppBar(
                        cartItems.size,
                        onCartClick = { navController.navigate(FurryScreens.Cart.name) })
                } else {
                    FurryAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        itemCount = cartItems.size,
                        onCartClick = { navController.navigate(FurryScreens.Cart.name) },
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = FurryScreens.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(route = FurryScreens.Start.name) {
                DashboardScreen(
                    onFriendClicked = {
                        viewModel.setSelectedFriend(it)
                        navController.navigate(FurryScreens.Friend.name)
                    },
                    onStoreItemClicked = {
                        viewModel.setSelectedStoreItem(it)
                        navController.navigate(FurryScreens.ItemDetail.name)
                    },
                    onStoreAreaClick = { navController.navigate(it.name) },
                    onAnimalClicked = {
                        viewModel.setStoreAnimalFilter(it)
                        navController.navigate(FurryScreens.Store.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            composable(route = FurryScreens.Friend.name) {
                val friend = viewModel.selectedFriend
                if (friend != null) {
                    MyFriendScreen(
                        friend,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
            composable(route = FurryScreens.ItemDetail.name) {
                val storeItem = viewModel.selectedStoreItem
                if (storeItem != null) {
                    ItemDetailScreen(
                        storeItem,
                        onAddToCart = { viewModel.addToCart(it) },
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
            composable(route = FurryScreens.Store.name) {
                val animalFilter = viewModel.storeAnimalFilter
                StoreScreen(
                    animalFilter = animalFilter,
                    onStoreItemClicked = {
                        viewModel.setSelectedStoreItem(it)
                        navController.navigate(FurryScreens.ItemDetail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            composable(route = FurryScreens.Cart.name) {
                CartScreen(
                    cartItems,
                    onAddItem = { viewModel.addToCart(it) },
                    onRemoveItem = { viewModel.removeFromCart(it) },
                    onCompleteOrder = {
                        viewModel.completeOrder()
                        navController.navigate(FurryScreens.Start.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
        }
    }
}