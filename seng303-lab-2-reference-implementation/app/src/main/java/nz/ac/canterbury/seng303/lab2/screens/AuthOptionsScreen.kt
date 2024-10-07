package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun AuthOptionsScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { navController.navigate("LoginScreen") }) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("RegisterScreen") }) {
            Text(text = "Sign Up")
        }
    }
}