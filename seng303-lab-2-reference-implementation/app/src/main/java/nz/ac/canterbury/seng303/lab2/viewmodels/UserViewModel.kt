package nz.ac.canterbury.seng303.lab2.viewmodels

import android.util.Log
import android.widget.Toast
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.datastore.Storage
import nz.ac.canterbury.seng303.lab2.models.Stall
import nz.ac.canterbury.seng303.lab2.models.User


class UserViewModel(private val userStorage: Storage<User>) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> get() = _users

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> get() = _isLoggedIn

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser


    init {
        getUsers() // Load users on initialization
    }

    fun getUsers() = viewModelScope.launch {
        userStorage.getAll()
            .catch { Log.e("USER_VIEW_MODEL", it.toString()) }
            .collect { usersList ->
                _users.emit(usersList)
            }
    }

    fun registerUser (user: User, onRegisterSuccess: () -> Unit = {}): Deferred<Unit> = viewModelScope.async {
        val existingUser  = userStorage.getAll().first().any { it.username == user.username }
        if (existingUser ) {
            Log.e("USER_VIEW_MODEL", "Username already exists")
        } else {
            userStorage.insert(user).collect { result ->
                if (result == 1) {
                    Log.e("USER_VIEW_MODEL", "User  registered successfully")
                    onRegisterSuccess()  // Call the success callback after successful registration
                }
            }
        }
    }

    suspend fun loginUser(username: String, password: String): Boolean {
        var isAuthenticated = false
        val allUsers = userStorage.getAll().first()
        Log.e("USER_VIEW_MODEL", "All users: $allUsers")
        Log.e("USER_VIEW_MODEL", "Username: $username, Password: $password")
        val user = allUsers.firstOrNull { it.username == username && it.password == password }
        isAuthenticated = user != null
        if (isAuthenticated) {
            _isLoggedIn.emit(true)
            _currentUser.emit(user)
        }
        return isAuthenticated
    }

    fun logout() {
            viewModelScope.launch {
                _isLoggedIn.emit(false)
            }
        }

    suspend fun getUserByUsername(username: String): User? {
        val allUsers = userStorage.getAll().first()  // Get current users
        return allUsers.firstOrNull { it.username == username }
    }

    fun loadDefaultNotesIfNoneExist() = viewModelScope.launch {
        val currentStalls = userStorage.getAll().first()
        if (currentStalls.isEmpty()) {
            Log.d("USER_VIEW_MODEL", "Adding Default Users...")

            userStorage.insertAll(User.getUsers()).catch {
                Log.w("userStorage", "Could not insert default users")
            }.collect {
                Log.d("USER_VIEW_MODEL", "Default users inserted successfully")
                _users.emit(User.getUsers())
            }
        }
    }


    fun updateUser(userId: Int, user: User) = viewModelScope.launch {
        if (userId != null) {
            userStorage.edit(user.id, user).collect { result ->
                if (result == 1) {
                    Log.d("USER_VIEW_MODEL", "User updated successfully")
                    _users.emit(userStorage.getAll().first())
                    _currentUser.emit(user)
                }
                else {
                    Log.e("USER_VIEW_MODEL", "Could not update user")
                }
            }
        }

    }


}







