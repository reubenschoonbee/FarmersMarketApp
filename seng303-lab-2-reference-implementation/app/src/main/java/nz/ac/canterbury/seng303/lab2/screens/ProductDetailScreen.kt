package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import nz.ac.canterbury.seng303.lab2.models.Stall
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel

@Composable
fun ProductDetailScreen(navController: NavHostController, stallViewModel: StallViewModel, productId: Int) {
    val selectedStallState by stallViewModel.selectedStall.collectAsState(null)
    val stall: Stall? = selectedStallState
    val product = stall?.products?.find { it.id == productId }
    var selectedQuantity by remember { mutableStateOf(1) }

    if (product != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(30.dp))
            // Product image
            Image(
                painter = painterResource(id = product.imageResId),
                contentDescription = product.name,
                modifier = Modifier
                    .height(250.dp)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Product name
            Text(
                text = product.name,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product price
            Text(
                text = "$${product.price}",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product description
            Text(
                text = product.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Quantity Selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { if (selectedQuantity > 1) selectedQuantity-- }, // Decrease quantity
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                    enabled = selectedQuantity > 1
                ) {
                    Text(text = "-")
                }

                Spacer(modifier = Modifier.width(16.dp))

                // Display selected quantity
                BasicTextField(
                    value = selectedQuantity.toString(),
                    onValueChange = {},
                    modifier = Modifier
                        .width(40.dp)
                        .background(Color.White)
                        .padding(4.dp),
                    enabled = false // Disable manual editing of quantity (just using + and - buttons)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { selectedQuantity++ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) {
                    Text(text = "+")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pre-order button
            Button(
                onClick = {
                    // DO PRE ORDER THING
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(text = "Pre-order $selectedQuantity items")
            }
        }

    } else {Text(text = "Could not find product", style = MaterialTheme.typography.headlineMedium)}
}

