package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import nz.ac.canterbury.seng303.lab2.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.items
import androidx.navigation.NavHostController
import nz.ac.canterbury.seng303.lab2.models.Product
import nz.ac.canterbury.seng303.lab2.models.Stall
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel

@Composable
fun ProductsScreen(navController: NavHostController, stallId: Int, stallViewModel: StallViewModel) {
    val selectedStallState by stallViewModel.selectedStall.collectAsState(null)
    val stall: Stall? = selectedStallState

    LaunchedEffect(stall) {
        stallViewModel.getStallById(stallId)
    }

    stall?.let {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()) {

            // Stall Name
            Text(text = it.name, style = MaterialTheme.typography.headlineLarge, modifier = Modifier.align(Alignment.CenterHorizontally))

            Spacer(modifier = Modifier.height(16.dp))

            // Section for highlighted products
            Text(
                text = "Check Out Our New Additions!",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                items(it.products.take(2)) { product ->  // Assume first 2 are new additions
                    ProductItem(product = product)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Section for displaying all products with a curved border
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
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(it.products) { product ->
                        ProductGridItem(product = product)
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
            painter = painterResource(id = R.drawable.default_image),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun ProductGridItem(product: Product) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.default_image),
            contentDescription = product.name,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Crop
        )
        Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
    }
}
