package nz.ac.canterbury.seng303.lab2.screens


import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import android.Manifest
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import nz.ac.canterbury.seng303.lab2.NotificationHandler
import nz.ac.canterbury.seng303.lab2.models.Market
import nz.ac.canterbury.seng303.lab2.scheduleWeeklyNotifications
import nz.ac.canterbury.seng303.lab2.viewmodels.MarketViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Home(navController: NavController, marketViewModel: MarketViewModel) {
    marketViewModel.getMarkets()
    val markets: List<Market> by marketViewModel.markets.collectAsState(emptyList())
    val context = LocalContext.current
    val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    val notificationHandler = NotificationHandler(context)

    LaunchedEffect(key1 = true) {
        if (!postNotificationPermission.status.isGranted) {
            postNotificationPermission.launchPermissionRequest()
        } else {
            scheduleWeeklyNotifications(context, markets)
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "View Our Markets!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Create the list of market cards
            items(markets) { market ->
                MarketCard(market, navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

    }
}

@Composable
fun MarketCard(market: Market, navController: NavController) {
    val isExpanded: Boolean = if (market.id == 1) true else false
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }
    val context = LocalContext.current



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
            Text(text = market.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = if (expanded) "▼" else "▲")
        }

        if (expanded) {
            Text(text = "Description: ${market.description}")
            Text(text = "Open Times: ${market.openTimes}")
            Text(text = "Location: ${market.location}")


                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    // Directions Icon
                    Icon(
                        imageVector = Icons.Default.Place,
                        contentDescription = "Get Directions",
                        modifier = Modifier.size(24.dp) // Adjust icon size if needed
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    // Clickable Link
                    Text(
                        text = "Get Directions",
                        modifier = Modifier
                            .clickable {
                                val uri = Uri.parse("http://maps.google.com/maps?daddr=${market.address}")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                                val mapIntent: Intent = Uri.parse("geo:0,0?q=${market.address}").let { location ->
                                    Intent(Intent.ACTION_VIEW, location).apply {
                                        putExtra("navigate", true)
                                    }
                                }
                                context.startActivity(mapIntent)


                            },
                        color = MaterialTheme.colorScheme.primary, // Link color
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline // Underline for link effect
                    )
                }


            Button(

                onClick = {
                    navController.navigate("StallsScreen/${market.id}") },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = "View Stalls")
            }
        }
    }
}