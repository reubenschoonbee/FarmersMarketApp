package nz.ac.canterbury.seng303.lab2.screens

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalConfiguration
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.navigation.NavHostController
import nz.ac.canterbury.seng303.lab2.models.Product
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel

@Composable
fun ProductDetailScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    stallViewModel: StallViewModel,
    productId: Int
) {
    val selectedStallState by stallViewModel.selectedStall.collectAsState(null)
    val product = selectedStallState?.products?.find { it.id == productId }
    val context = LocalContext.current
    var selectedQuantity by remember { mutableStateOf(1) }

    // Detect current orientation
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (product != null) {
        if (isLandscape) {
            // Landscape layout: image on the left, details on the right
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.width(50.dp))

                // Product image
                Image(
                    painter = painterResource(id = product.imageResId),
                    contentDescription = product.name,
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(40.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Product details in a Column
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ProductDetails(
                        product = product,
                        selectedQuantity = selectedQuantity,
                        onQuantityChange = { selectedQuantity = it },
                        onPreOrder = {
                            handlePreOrder(userViewModel, context)
                        }
                    )
                }
            }
        } else {
            // Portrait layout: stacked vertically
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
                        .align(Alignment.CenterHorizontally)
                        .clip(RoundedCornerShape(40.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product details
                ProductDetails(
                    product = product,
                    selectedQuantity = selectedQuantity,
                    onQuantityChange = { selectedQuantity = it },
                    onPreOrder = {
                        handlePreOrder(userViewModel, context)
                    }
                )
            }
        }
    }

}

fun handlePreOrder(userViewModel: UserViewModel, context: android.content.Context) {
    if (userViewModel.isLoggedIn.value) {
        Toast.makeText(context, "Pre Order Request Successful", Toast.LENGTH_SHORT).show()
        // TODO: Handle pre-order
    } else {
        Toast.makeText(context, "User must be logged in to pre order", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ProductDetails(
    product: Product,
    selectedQuantity: Int,
    onQuantityChange: (Int) -> Unit,
    onPreOrder: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Product name
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineLarge,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Product price
        Text(
            text = "$${product.price}",
            style = MaterialTheme.typography.headlineMedium,
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
                onClick = { if (selectedQuantity > 1) onQuantityChange(selectedQuantity - 1) },
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
                    .background(Color.Transparent)
                    .padding(13.dp),
                enabled = false
            )

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { onQuantityChange(selectedQuantity + 1) },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "+")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Pre-order button
        Button(
            onClick = onPreOrder,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 56.dp),
            contentPadding = PaddingValues(6.dp)
        ) {
            Text(text = "Pre-order $selectedQuantity items")
        }
    }
}
