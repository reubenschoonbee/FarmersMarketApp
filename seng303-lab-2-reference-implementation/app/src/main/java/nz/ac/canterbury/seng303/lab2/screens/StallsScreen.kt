package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.TextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import androidx.compose.material3.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import nz.ac.canterbury.seng303.lab2.models.Stall

@Composable
fun StallsScreen(navController: NavController, stallViewModel: StallViewModel, marketId: Int?) {
    val stalls: List<Stall> by stallViewModel.stalls.collectAsState(emptyList())
    val categories by stallViewModel.categories.collectAsState(initial = emptyList())

    var selectedCategory by rememberSaveable { mutableStateOf("Categories") }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val updatedCategories = listOf("Categories") + categories

    // Trigger fetching of stalls when the screen is loaded
    LaunchedEffect(marketId) {
        if (marketId != null) {
            stallViewModel.getStalls(marketId)
        }
    }
    LaunchedEffect(categories) {
        // Log or debug output to verify the categories
        println("Fetched categories for marketId $marketId: $categories")
    }
    // Filter stalls based on selected category and search query
    val filteredStalls = stalls.filter { stall ->
        (selectedCategory == "Categories" || stall.category == selectedCategory) &&
                (searchQuery.isEmpty() || stall.name.contains(searchQuery, ignoreCase = true))
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
//            .background(Color(0xFFF0F0F0)) // Light gray background
    ) {
        // Filtering Component
        item {
            StallFilterComponent(
                selectedCategory = selectedCategory,
                categories = updatedCategories,
                onCategorySelected = { category ->
                    selectedCategory = category
                },
                searchQuery = searchQuery,
                onSearchQueryChange = { query ->
                    searchQuery = query
                }
            )
        }


        // Stall Cards in a 2-column grid
        items(filteredStalls.chunked(2)) {stallPair ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                stallPair.forEach { stall ->
                    StallCard(navController, stall, modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun StallCard(
    navController: NavController,
    stall: Stall,
    modifier: Modifier = Modifier
) {
    val baseUrl = "https://mohadesasharifi.github.io/Assignment2_seng303/"


    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
//            .padding(8.dp)
//            .fillMaxWidth()
            .clickable { navController.navigate("ProductsScreen/${stall.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center, // Centers content vertically
            horizontalAlignment = Alignment.CenterHorizontally // Centers content horizontally
        ) {
            // Stall Image
            Image(
                painter = painterResource(id = stall.imageResId),
                contentDescription = stall.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Stall Name
            Text(
                text = stall.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            // View Products Button
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                FilledTonalButton(onClick = { navController.navigate("ProductsScreen/${stall.id}") }) {
                    Text("View Products")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Share(text = "Check out ${stall.name} at our farmers market! Visit: $baseUrl", context = LocalContext.current)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StallFilterComponent(
    selectedCategory: String,
    categories: List<String>,
    onCategorySelected: (String) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) } // Controls dropdown visibility

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // Margin around the whole filter component
    ) {
        // Search Bar

        // Category Selector
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .padding(bottom = 8.dp)
                .border(
                    width = 1.dp,
                    color = Color(0xFFD1E5F4), // Light blue border color
                    shape = RoundedCornerShape(8.dp)
                )
                .background(Color(0xFFEAF4FB))
                .clickable { expanded = true } // Open dropdown on click
                .padding(16.dp) // Padding inside the Box

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // Space between text and dropdown arrow
            ) {
                Text(text = selectedCategory, style = MaterialTheme.typography.bodyLarge) // Category Text
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown arrow",
                    tint = MaterialTheme.colorScheme.primary
                ) // Dropdown Arrow
            }

            // Dropdown Menu
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(Color.White) // White background
                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)) // Add border
                    .padding(8.dp) // Padding inside the dropdown
            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        text = {
                            Text(text = category)
                        },
                        onClick = {
                            onCategorySelected(category) // Handle category selection
                            expanded = false
                        },
                        modifier = Modifier.fillMaxWidth(), // Ensure it takes full width
                    )
                }
            }
        }
        Row {
            TextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD1E5F4), // Light blue border color
                        shape = RoundedCornerShape(8.dp)
                    ),
                placeholder = { Text("Search...", color = Color.Gray) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xFFEAF4FB)
                )
            )

        }
    }
}


