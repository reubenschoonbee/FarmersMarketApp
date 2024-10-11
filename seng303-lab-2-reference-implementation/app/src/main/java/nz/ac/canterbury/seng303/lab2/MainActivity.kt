package nz.ac.canterbury.seng303.lab2

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import nz.ac.canterbury.seng303.lab2.screens.AuthOptionsScreen
import nz.ac.canterbury.seng303.lab2.screens.Home
import nz.ac.canterbury.seng303.lab2.screens.ProductsScreen
import nz.ac.canterbury.seng303.lab2.screens.MyProfileScreen
import nz.ac.canterbury.seng303.lab2.screens.StallsScreen
import nz.ac.canterbury.seng303.lab2.ui.theme.Lab1Theme
import nz.ac.canterbury.seng303.lab2.viewmodels.MarketViewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel
import nz.ac.canterbury.seng303.lab2.screens.LoginScreen
import nz.ac.canterbury.seng303.lab2.screens.PreferencesScreen
import nz.ac.canterbury.seng303.lab2.screens.RegisterScreen
import org.koin.androidx.viewmodel.ext.android.viewModel as koinViewModel
import nz.ac.canterbury.seng303.lab2.screens.ProductDetailScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.ThemeViewModel
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import nz.ac.canterbury.seng303.lab2.models.Market
import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import nz.ac.canterbury.seng303.lab2.screens.Share


class MainActivity : ComponentActivity() {

    private val marketViewModel: MarketViewModel by koinViewModel()
    private val stallViewModel: StallViewModel by koinViewModel()
    val userViewModel: UserViewModel by koinViewModel()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Delete the datastore on launch
        if (false) {
            marketViewModel.deleteAll()
            stallViewModel.deleteAll()
        }

        // Load the test data if none exist
        marketViewModel.loadDefaultNotesIfNoneExist()
        stallViewModel.loadDefaultNotesIfNoneExist()
        userViewModel.loadDefaultNotesIfNoneExist()

        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
            val context = LocalContext.current
            val isFirstLaunch = remember { mutableStateOf(true) }
            marketViewModel.getMarkets()
            val markets: List<Market> by marketViewModel.markets.collectAsState(emptyList())
            val postNotificationPermission = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
            val isPermissionGranted = remember { mutableStateOf(false) }
            val navController = rememberNavController()

            LaunchedEffect(intent?.data) {
                intent?.data?.let { uri ->
                    val stallId = uri.lastPathSegment?.toIntOrNull()
                    stallId?.let {
                        navController.navigate("ProductsScreen/$stallId")
                    }
                }
            }
            // LaunchedEffect to request permission
            LaunchedEffect(Unit) {
                if (!postNotificationPermission.status.isGranted) {
                    postNotificationPermission.launchPermissionRequest()
                }
            }

            // LaunchedEffect to check permission status
            LaunchedEffect(postNotificationPermission.status) {
                if (postNotificationPermission.status.isGranted) {
                    isPermissionGranted.value = true
                } else {
                    isPermissionGranted.value = false
                }
            }

            // LaunchedEffect to schedule notifications when permission is granted
            LaunchedEffect(isPermissionGranted.value) {
                if (isPermissionGranted.value) {
                    scheduleWeeklyNotifications(context, markets)
                }
            }

            Lab1Theme(isDarkTheme) {

                val isLoggedIn by userViewModel.isLoggedIn.collectAsState()
                // A surface container using the 'background' color from the theme

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
                            },
                            actions = {
                                IconButton(onClick = {
                                    navController.navigate("PreferencesScreen")
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Settings,
                                        contentDescription = "Settings"
                                    )
                                }
                                IconButton(onClick = {
                                    if (isLoggedIn) {
                                        navController.navigate("MyProfileScreen")
                                    } else {
                                        navController.navigate("AuthOptionsScreen")
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = "Profile"
                                    )
                                }
                            }
                        )
                    }
                ) {
                    Box(modifier = Modifier.padding(it)) {
                        NavHost(navController = navController, startDestination = "Home") {
                            composable("Home") {
                                Home(navController = navController, markets, context)
                            }

                            composable("StallsScreen/{marketId}") { backStackEntry ->
                                val marketId =
                                    backStackEntry.arguments?.getString("marketId")?.toInt()
                                StallsScreen(
                                    navController = navController,
                                    stallViewModel = stallViewModel,
                                    marketId = marketId
                                )
                            }

                            composable("ProductsScreen/{stallId}") { backStackEntry ->
                                val stallId =
                                    backStackEntry.arguments?.getString("stallId")?.toInt()
                                stallId?.let { ProductsScreen(navController, it, stallViewModel, userViewModel) }
                            }

                            composable("ProductDetailScreen/{productId}") { backStackEntry ->
                                val productId =
                                    backStackEntry.arguments?.getString("productId")?.toInt()
                                productId?.let {
                                    ProductDetailScreen(
                                        userViewModel = userViewModel,
                                        stallViewModel = stallViewModel,
                                        productId = productId
                                    )
                                }
                            }
                            composable("AuthOptionsScreen") {
                                AuthOptionsScreen(navController)
                            }
                            composable("MyProfileScreen") {
                                MyProfileScreen(navController, userViewModel)
                            }
                            composable("LoginScreen") {
                                LoginScreen(userViewModel, navController)
                            }
                            composable("RegisterScreen") {
                                RegisterScreen(userViewModel, navController)
                            }
                            composable("PreferencesScreen") {
                                PreferencesScreen(userViewModel, navController, themeViewModel, isPermissionGranted)
                            }
                        }
                    }
                }
            }
        }
    }
}
