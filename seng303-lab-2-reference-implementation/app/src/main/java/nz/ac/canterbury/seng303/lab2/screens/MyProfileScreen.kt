package nz.ac.canterbury.seng303.lab2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel

@Composable
fun MyProfileScreen(navController: NavController, userViewModel: UserViewModel) {
    val context = LocalContext.current

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
            Toast.makeText(context, "Logout successful", Toast.LENGTH_SHORT).show()
            navController.navigate("Home") {
                popUpTo("Home") { inclusive = true }
            }
        }) {
            Text(text = "Logout")
        }
    }
}