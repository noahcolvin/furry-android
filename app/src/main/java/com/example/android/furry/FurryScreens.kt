package com.example.android.furry

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
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
fun ImageAppBar() {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.header_logo),
                contentDescription = "Furry",
                modifier = Modifier.width(110.dp)
            )
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.ShoppingCart,
                    contentDescription = "Localized description"
                )
            }
        })
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

    Scaffold(
        topBar = {
            currentScreen.let {
                if (it == FurryScreens.Start) {
                    ImageAppBar()
                } else {
                    FurryAppBar(
                        currentScreen = currentScreen,
                        canNavigateBack = navController.previousBackStackEntry != null,
                        navigateUp = { navController.navigateUp() }
                    )
                }
            }
        }
    ) { innerPadding ->
        //val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = FurryScreens.Start.name,
            modifier = Modifier
                .fillMaxSize()
                //.verticalScroll(rememberScrollState())
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
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }
            }
            composable(route = FurryScreens.Store.name) {
                StoreScreen(
                    onStoreItemClicked = {
                        viewModel.setSelectedStoreItem(it)
                        navController.navigate(FurryScreens.ItemDetail.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }
            /*composable(route = FurryScreen.Flavor.name) {
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(FurryScreen.Pickup.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = FurryScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(FurryScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }
            composable(route = FurryScreen.Summary.name) {
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { subject: String, summary: String ->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }*/
        }
    }
}