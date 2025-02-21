package com.example.android.furry.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.android.furry.domain.StoreItem
import com.example.android.furry.util.allIfNullRemoveTrailingS

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(
    modifier: Modifier = Modifier,
    animalFilter: String? = null,
    viewModel: StoreScreenViewModel = viewModel(factory = StoreScreenViewModel.Factory),
    onStoreItemClicked: (StoreItem) -> Unit
) {
    val storeItems by viewModel.storeItemsList.collectAsState()
    var selectedAnimal by remember { mutableStateOf(allIfNullRemoveTrailingS(animalFilter)) }
    var selectedProduct by remember { mutableStateOf("All") }

    val animals = listOf("All", "Dog", "Cat", "Hamster", "Bird", "Fish")
    val products = listOf("All", "Food", "Toy")

    val searchText by viewModel.searchText.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    LaunchedEffect(Unit) {
        if (selectedAnimal !== "All") {
            viewModel.getStoreItemsList(selectedAnimal, selectedProduct)
        } else {
            viewModel.getStoreItemsList()
        }
    }

    Column(modifier = modifier) {
        SearchBar(
            query = searchText,
            onQueryChange = { viewModel.onSearchTextChanged(it) },
            onSearch = { viewModel.onSearchChanged(false) },
            active = isSearching,
            onActiveChange = { viewModel.onSearchChanged(it) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        viewModel.onSearchTextChanged("")
                        viewModel.onSearchChanged(false)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Clear search"
                        )
                    }
                }
            },
            windowInsets = WindowInsets(top = 0.dp),
            modifier = Modifier
                .fillMaxWidth()

        ) {
            if (storeItems.isNullOrEmpty()) {
                Text(
                    text = "Sorry, no items available",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(8.dp)
                )
            } else {
                LazyColumn {
                    items(storeItems!!) { item ->
                        Text(
                            text = item.name,
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 4.dp,
                                    end = 8.dp,
                                    bottom = 4.dp
                                )
                                .clickable { onStoreItemClicked(item) }
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()
        ) {
            Dropdown(animals, "Animals", selectedAnimal, onSelectCategory = {
                selectedAnimal = it
                viewModel.getStoreItemsList(selectedAnimal, selectedProduct)
            })
            Dropdown(products, "Products", "All", onSelectCategory = {
                selectedProduct = it
                viewModel.getStoreItemsList(selectedAnimal, selectedProduct)
            })
        }

        if (storeItems.isNullOrEmpty()) {
            Text(
                text = "Sorry, no items available",
                fontSize = 14.sp,
                modifier = Modifier.padding(8.dp)
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2)
            ) {
                items(storeItems!!.size) { index ->
                    StoreItem(
                        item = storeItems!![index],
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable { onStoreItemClicked(storeItems!![index]) })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Dropdown(
    categories: List<String>,
    label: String,
    initialCategory: String,
    onSelectCategory: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(initialCategory) }

    val width = LocalConfiguration.current.screenWidthDp.dp / 2f - 20.dp

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.width(width)
    ) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = { },
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .clickable { expanded = true }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            categories.forEach { category ->
                Text(
                    text = category,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSelectCategory(category)
                            selectedCategory = category
                            expanded = false
                        }
                        .padding(8.dp)
                )
            }
        }
    }
}