package nz.ac.canterbury.seng303.lab2.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
@Composable
fun StallScreen(navController: NavHostController, stallId: Int, stallViewModel: StallViewModel) {
    Column {
        Text(text = "Stall ID: $stallId")
    }
}