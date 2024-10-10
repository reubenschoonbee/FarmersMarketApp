package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavHostController
import nz.ac.canterbury.seng303.lab2.models.Product
import nz.ac.canterbury.seng303.lab2.models.Stall
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import nz.ac.canterbury.seng303.lab2.R

@Composable
fun ProductsScreen(navController: NavHostController, stallId: Int, stallViewModel: StallViewModel) {
    stallViewModel.getStallById(stallId)
    val selectedStallState by stallViewModel.selectedStall.collectAsState(null)
    val stall: Stall? = selectedStallState

    // Detecting the current orientation of the screen
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE

    if (stall != null) {
        if (isLandscape) {
            // Landscape mode layout
            Row(modifier = Modifier.padding(16.dp).fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stall.name,
                        style = MaterialTheme.typography.headlineLarge,
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Check Out Our New Additions!",
                        style = MaterialTheme.typography.headlineSmall,
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Featured products in a row
                    LazyRow {
                        items(stall.products.take(2)) { product ->
                            ProductItem(product)
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .border(
                            BorderStroke(2.dp, Color.Gray),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                ) {
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(1),
                        contentPadding = PaddingValues(18.dp),
                    ) {
                        items(stall.products) { product ->
                            ProductGridItem(navController, product)
                        }
                    }
                }
            }
        } else {
            // Portrait mode
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {

                // Stall Name
                Text(
                    text = stall.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Section for highlighted products
                Text(
                    text = "Check Out Our New Additions!",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    items(stall.products.take(2)) { product ->  // Assume first 2 are the featured products
                        ProductItem(product)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(
                            BorderStroke(2.dp, Color.Gray),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(8.dp)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(8.dp),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        items(stall.products) { product ->
                            ProductGridItem(navController, product)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp).clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop
        )
        Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ProductGridItem(navController: NavHostController,product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                navController.navigate("ProductDetailScreen/${product.id}")
            }
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = product.imageResId),
            contentDescription = product.name,
            modifier = Modifier
                .size(100.dp)
                .padding(4.dp)
                .clip(RoundedCornerShape(40.dp)),
            contentScale = ContentScale.Crop
        )
        Text(
            text = product.name,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "$" + product.price.toString(), style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = if (product.quantity > 0) R.drawable.in_stock else R.drawable.out_of_stock),
                contentDescription = if (product.quantity > 0) "In stock" else "Out of stock",
                modifier = Modifier
                    .size(20.dp)
                    .padding(start = 4.dp)
            )
        }
    }
}

