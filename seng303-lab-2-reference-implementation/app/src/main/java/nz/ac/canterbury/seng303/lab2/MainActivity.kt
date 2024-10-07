package nz.ac.canterbury.seng303.lab2

import AllStallsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.seng303.lab2.screens.StallScreen
//import nz.ac.canterbury.seng303.lab2.screens.AllStallsScreen
import nz.ac.canterbury.seng303.lab2.ui.theme.Lab1Theme
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel // Import StallViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import nz.ac.canterbury.seng303.lab2.screens.MarketCard
import androidx.compose.ui.res.stringResource

class MainActivity : ComponentActivity() {

    private val stallViewModel: StallViewModel by koinViewModel() // Use StallViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Lab1Theme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Farmers Market") },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Back"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination = "Home") {
                            composable("Home") {
                                Home(navController= navController, stallViewModel)
                            }
                            composable("StallDetail/{stallId}") { backStackEntry ->
                                val stallId = backStackEntry.arguments?.getString("stallId")?.toInt()
                                stallId?.let { StallScreen(navController, it, stallViewModel) }
                            }
                            composable("AllStallsScreen/{marketId}") { backStackEntry ->
                                val marketId = backStackEntry.arguments?.getString("marketId")?.toInt()
                                AllStallsScreen(navController = navController, stallViewModel = stallViewModel, marketId = marketId)
                            }

                        }
                    }
                }
            }
        }
    }


}

@Composable
fun Home(navController: NavController, stallViewModel: StallViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "View Our Markets!",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Market 1 (Expanded by default)
        MarketCard(
            marketId = 1,
            marketName = stringResource(R.string.market1),
            description = stringResource(R.string.market1_description),
            openTimes = stringResource(R.string.market1_open_times),
            location = stringResource(R.string.market1_location),
            address = stringResource(R.string.market1_address),
            isExpanded = true,
            navController = navController,
            stallViewModel = stallViewModel
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Market 2
        MarketCard(
            marketId = 2,
            marketName = stringResource(R.string.market2),
            description = stringResource(R.string.market2_description),
            openTimes = stringResource(R.string.market2_open_times),
            location = stringResource(R.string.market2_location),
            address = stringResource(R.string.market2_address),
            isExpanded = false,
            navController = navController,
            stallViewModel = stallViewModel
        )

    }
}




