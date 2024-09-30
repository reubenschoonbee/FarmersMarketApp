package nz.ac.canterbury.seng303.lab2.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.models.Identifiable
import nz.ac.canterbury.seng303.lab2.models.Market1Stalls
import nz.ac.canterbury.seng303.lab2.models.Market2Stalls
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel

@Composable
fun AllStallsScreen(navController: NavController, stallViewModel: StallViewModel, marketId: Int?) {
    Log.d("YourTag", "This is a debug message $marketId")

    // Fetch the stalls when the screen is first composed
    LaunchedEffect(marketId) {
        stallViewModel.getStalls(marketId)
    }

    // Collect the stalls from the ViewModel as a general Identifiable type
    val stalls: List<Identifiable> by stallViewModel.stalls.collectAsState(emptyList())

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Specify the number of columns in the grid
        contentPadding = PaddingValues(4.dp, 8.dp),
        modifier = Modifier.background(Color.LightGray)
    ) {
        items(stalls) { stall ->
            // Cast the stall to Identifiable
            stallsScreenItem(navController = navController, stall = stall)
        }
    }
}

@Composable
fun stallsScreenItem(navController: NavController, stall: Identifiable) {
    when (stall) {
        is Market1Stalls -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { navController.navigate("StallDetail/${stall.getIdentifier()}") } // Navigate to stall detail on click
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Box {
                        // Optionally include an image here
                        /*
                        Image(
                            painter = painterResource(id = stall.id), // Use the resolved image resource
                            contentDescription = stall.name, // Stall name as content description
                            modifier = Modifier
                                .height(120.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                        )
                        */

                        TextButton(
                            onClick = { navController.navigate("StallDetail/${stall.getIdentifier()}") }, // Navigate to stall detail
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(text = "View")
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(modifier = Modifier) {
                        Text(
                            text = stall.name, // Access name from Market1Stalls
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stall.category, // Access category from Market1Stalls
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        is Market2Stalls -> {
            // Similar implementation for Market2Stalls
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { navController.navigate("StallDetail/${stall.getIdentifier()}") } // Navigate to stall detail on click
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .padding(2.dp)
                        .fillMaxWidth()
                ) {
                    Box {
                        TextButton(
                            onClick = { navController.navigate("StallDetail/${stall.getIdentifier()}") }, // Navigate to stall detail
                            modifier = Modifier.align(Alignment.BottomEnd)
                        ) {
                            Text(text = "View")
                        }
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Column(modifier = Modifier) {
                        Text(
                            text = stall.name, // Access name from Market2Stalls
                            style = MaterialTheme.typography.bodyLarge,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stall.category, // Access category from Market2Stalls
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        else -> {
            // Handle the case for an unknown stall type if necessary
            Log.w("StallsScreen", "Unknown stall type: ${stall::class.simpleName}")
        }
    }
}
