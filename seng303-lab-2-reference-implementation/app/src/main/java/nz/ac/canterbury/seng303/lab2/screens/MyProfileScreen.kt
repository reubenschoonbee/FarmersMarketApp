package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel

@Composable
fun MyProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User Profile")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            userViewModel.logout()
            navController.navigate("Home") {
                popUpTo("Home") { inclusive = true }
            }
        }) {
            Text(text = "Logout")
        }
    }
}