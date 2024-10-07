package nz.ac.canterbury.seng303.lab2.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.datastore.Storage
import nz.ac.canterbury.seng303.lab2.models.User


class UserViewModel(private val userStorage: Storage<User>) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    fun getUsers() = viewModelScope.launch {
        userStorage.getAll().catch { Log.e("USER_VIEW_MODEL", it.toString()) }
            .collect { _users.emit(it) }
    }

    fun registerUser(user: User) = viewModelScope.launch {
        if (userStorage.getAll().first().any { it.username == user.username }) {
            Log.e("USER_VIEW_MODEL", "Username already exists")
        }
        else {
            userStorage.insert(user).collect { Log.e("USER_VIEW_MODEL", it.toString()) }
        }

    }

    fun loginUser(username: String, password: String): Boolean {
        var isAuthenticated = false
        viewModelScope.launch {
            val allUsers = userStorage.getAll().first()
            val user = allUsers.firstOrNull { it.username == username && it.password == password }
            isAuthenticated = user != null
        }
        return isAuthenticated
    }
}
