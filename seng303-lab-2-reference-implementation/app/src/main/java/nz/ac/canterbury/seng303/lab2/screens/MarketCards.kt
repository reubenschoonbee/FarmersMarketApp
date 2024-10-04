package nz.ac.canterbury.seng303.lab2.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel


@Composable
fun MarketCard(
    marketId: Int,
    marketName: String,
    description: String,
    openTimes: String,
    location: String,
    isExpanded: Boolean,
    navController: NavController,
    stallViewModel:StallViewModel
) {
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = marketName, style = MaterialTheme.typography.bodyLarge)
            Text(text = if (expanded) "▼" else "▲")
        }

        if (expanded) {
            Text(text = "Description: $description")
            Text(text = "Open Times: $openTimes")
            Text(text = "Location: $location")

            Spacer(modifier = Modifier.height(8.dp))

            Button(

                onClick = {
                    stallViewModel.loadDefaultStallsIfNoneExist(marketId)
                    navController.navigate("AllStallsScreen/$marketId") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "View Stalls")
            }
        }
    }
}