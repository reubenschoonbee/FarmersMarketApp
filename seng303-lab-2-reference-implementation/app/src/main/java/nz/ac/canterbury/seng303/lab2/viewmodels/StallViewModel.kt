package nz.ac.canterbury.seng303.lab2.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.datastore.Storage
import nz.ac.canterbury.seng303.lab2.models.Stall

class StallViewModel(private val stallStorage: Storage<Stall>) : ViewModel() {
    private val _stalls = MutableStateFlow<List<Stall>>(emptyList())
    val stalls: StateFlow<List<Stall>> get() = _stalls

//    private val _selectedStall = MutableStateFlow<Identifiable?>(null)
//    val selectedStall: StateFlow<Identifiable?> = _selectedStall
//
//    private val _stallByName = MutableStateFlow<Identifiable?>(null)
//    val stallByName: StateFlow<Identifiable?> = _stallByName

    // Auto update categories when stalls change
    val categories: StateFlow<List<String>> = stalls
        .map { stallList ->
            stallList.map { it.category }.distinct()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )

    // Get list of stalls with the specified marketId
    fun getStalls(marketId: Int?) = viewModelScope.launch {
        stallStorage.getAll().catch { Log.e("STALL_VIEW_MODEL", it.toString()) }
            .collect { stallList ->
                val filteredStalls = if (marketId != null) {
                    stallList.filter { it.marketIds.contains(marketId) }
                } else {
                    stallList
                }
                _stalls.emit(filteredStalls)
            }
    }

    // Deletes all stalls in the datastore
    fun deleteAll() = viewModelScope.launch {
        stallStorage.deleteAll().collect {}
    }

    // Function to fetch stall by name and marketId
//    fun getStallByName(marketId: Int, stallName: String) = viewModelScope.launch {
//        // Collect the result from the storage, based on marketId
//        stallStorage.getStallByName(marketId, stallName).collect { fetchedStall ->
//            _stallByName.value = fetchedStall // Now the fetched stall can be Market1Stalls or Market2Stalls
//        }
//    }


//    fun getStallById(stallId: Int?) = viewModelScope.launch {
//        if (stallId != null) {
//            _selectedStall.value = stallStorage.get { it.getIdentifier() == stallId }.first()
//        } else {
//            _selectedStall.value = null
//        }
//    }

    fun loadDefaultNotesIfNoneExist() = viewModelScope.launch {
        val currentStalls = stallStorage.getAll().first()
        if (currentStalls.isEmpty()) {
            Log.d("STALL_VIEW_MODEL", "Adding Test Stalls...")

            stallStorage.insertAll(Stall.getStalls()).catch {
                Log.w("stallStorage", "Could not insert test Stalls")
            }.collect {
                Log.d("STALL_VIEW_MODEL", "Test Stalls inserted successfully")
                _stalls.emit(Stall.getStalls())
            }
        }
    }

}

