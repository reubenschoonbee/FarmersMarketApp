package nz.ac.canterbury.seng303.lab2.screens


import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.util.Log
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient

@Composable
fun MarketCard(
    marketId: Int,
    marketName: String,
    description: String,
    openTimes: String,
    location: String,
    address:String,
    isExpanded: Boolean,
    navController: NavController,
    stallViewModel:StallViewModel
) {
    var expanded by rememberSaveable { mutableStateOf(isExpanded) }
    val context = LocalContext.current
    var currentLocation by remember { mutableStateOf<String?>(null) }
    var currentLatitude by remember { mutableStateOf<Double?>(null) }
    var currentLongitude by remember { mutableStateOf<Double?>(null) }



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
            Text(
                text = marketName,
                style = MaterialTheme.typography.titleMedium, // Use headline style for title
                fontWeight = FontWeight.Bold // Make it bold
            )
            Text(text = if (expanded) "▼" else "▲")
        }

        if (expanded) {
            Column(modifier = Modifier.padding(start = 8.dp, top = 4.dp, bottom = 8.dp)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = ParagraphStyle(textIndent = TextIndent(firstLine = TextUnit.Unspecified, restLine = 16.sp))) {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Description: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) { // Less bold than the label
                                append(description)
                            }
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Open Times: ")
                        }
                        append(openTimes) // Value is not bold
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Location: ")
                        }
                        append(location) // Value is not bold
                    },
                    style = MaterialTheme.typography.bodyMedium
                )
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
                                val uri = Uri.parse("http://maps.google.com/maps?daddr=$address")
                                val intent = Intent(Intent.ACTION_VIEW, uri)
                                context.startActivity(intent)
                                val mapIntent: Intent = Uri.parse("geo:0,0?q=$address").let { location ->
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
                        stallViewModel.loadDefaultStallsIfNoneExist(marketId)
                        navController.navigate("AllStallsScreen/$marketId")
                    },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = "View Stalls")
                }
            }
        }
    }
}
