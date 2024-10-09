package nz.ac.canterbury.seng303.lab2.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.models.User
import nz.ac.canterbury.seng303.lab2.viewmodels.UserViewModel

@Composable
fun PreferencesScreen(userViewModel: UserViewModel, navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val currentUser by userViewModel.currentUser.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Preferences")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { showDialog = true }) {
            Text("Edit Profile")
        }

        if (showDialog) {
            currentUser?.let { user ->
                EditProfileDialog(
                    currentUser = user,
                    onDismiss = { showDialog = false },
                    onSave = { newUsername, newPassword ->
                        scope.launch {
                            if (newUsername.isEmpty() || newPassword.isEmpty()) {
                                Toast.makeText(context, "Username and password cannot be empty", Toast.LENGTH_SHORT).show()
                            } else if (newUsername == user.username && newPassword == user.password) {
                                Toast.makeText(context, "New username and password cannot be the same as the current ones", Toast.LENGTH_SHORT).show()
                            } else {
                                userViewModel.updateUser(user.id, User(user.id, newUsername, newPassword))
                                Toast.makeText(context, "User information updated", Toast.LENGTH_SHORT).show()
                                showDialog = false
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun EditProfileDialog(currentUser: User, onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var newUsername by remember { mutableStateOf(currentUser.username) }
    var newPassword by remember { mutableStateOf(currentUser.password) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Profile") },
        text = {
            Column {
                TextField(
                    value = newUsername,
                    onValueChange = { newUsername = it },
                    label = { Text("New Username") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = newPassword,
                    onValueChange = { newPassword = it },
                    label = { Text("New Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(newUsername, newPassword) }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}