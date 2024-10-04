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
import nz.ac.canterbury.seng303.lab2.models.Identifiable
import nz.ac.canterbury.seng303.lab2.models.Market1Stalls
import nz.ac.canterbury.seng303.lab2.models.Market2Stalls

class StallViewModel(private val stallStorage: Storage<out Identifiable>) : ViewModel() {
    private val _selectedStall = MutableStateFlow<Identifiable?>(null)
    val selectedStall: StateFlow<Identifiable?> = _selectedStall
    private val _stalls = MutableStateFlow<List<Identifiable>>(emptyList())
    val stalls: StateFlow<List<Identifiable>> get() = _stalls
    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories
    private val _stallByName = MutableStateFlow<Identifiable?>(null)
    val stallByName: StateFlow<Identifiable?> = _stallByName

    // Function to fetch stall by name and marketId
    fun getStallByName(marketId: Int, stallName: String) = viewModelScope.launch {
        // Collect the result from the storage, based on marketId
        stallStorage.getStallByName(marketId, stallName).collect { fetchedStall ->
            _stallByName.value = fetchedStall // Now the fetched stall can be Market1Stalls or Market2Stalls
        }
    }

    fun getStallById(stallId: Int?) = viewModelScope.launch {
        if (stallId != null) {
            _selectedStall.value = stallStorage.get { it.getIdentifier() == stallId }.first()
        } else {
            _selectedStall.value = null
        }
    }
    fun getStalls(marketId: Int?) = viewModelScope.launch {
        // Fetch stalls for the specified marketId
        val stalls = when (marketId) {
            1 -> Market1Stalls.getStalls(marketId)
            2 -> Market2Stalls.getStalls(marketId)
            else -> emptyList() // Handle invalid marketId
        }

        // Emit the fetched stalls
        _stalls.emit(stalls)
    }

    // Function to fetch categories by marketId
    fun getStallCategories(marketId: Int) = viewModelScope.launch {
        stallStorage.getCategories(marketId).collect { fetchedCategories ->
            _categories.value = fetchedCategories
        }
    }
//    fun getStallsCategory(marketId: Int?) = viewModelScope.launch {  {
//        val categories = when (marketId) {
//            1 -> Market1Stalls.getCategories(marketId)
//            2 -> Market2Stalls.getCategories(marketId)
//            else -> emptyList() // Handle invalid marketId
//        }
//    } }
    fun loadDefaultStallsIfNoneExist(marketId: Int) = viewModelScope.launch {
        // Fetch current stalls with the specified marketId
        val currentStalls = stallStorage.getAll(marketId).first()
        // Check if the current stalls list is empty
        if (currentStalls.isEmpty()) {
            Log.d("STALL_VIEW_MODEL", "Inserting default stalls...")

            // Insert default stalls based on the marketId
            val defaultStalls: List<Identifiable> = when (marketId) {
                1 -> Market1Stalls.getStalls(marketId) as List<Identifiable>
                2 -> Market2Stalls.getStalls(marketId) as List<Identifiable>
                else -> emptyList() // Handle invalid marketId if needed
            }

            // Insert the default stalls into storage
            stallStorage.insertAll(defaultStalls).catch { exception ->
                Log.w("STALL_VIEW_MODEL", "Could not insert default stalls: ${exception.message}")
            }.collect {
                Log.d("STALL_VIEW_MODEL", "Default stalls inserted successfully")
                // Emit the newly inserted default stalls
                _stalls.emit(defaultStalls)
            }
        }
    }

}

