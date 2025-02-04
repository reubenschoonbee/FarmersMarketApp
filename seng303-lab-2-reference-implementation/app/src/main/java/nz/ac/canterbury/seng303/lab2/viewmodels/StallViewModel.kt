package nz.ac.canterbury.seng303.lab2.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nz.ac.canterbury.seng303.lab2.datastore.Storage
import nz.ac.canterbury.seng303.lab2.models.Review
import nz.ac.canterbury.seng303.lab2.models.Stall

class StallViewModel(private val stallStorage: Storage<Stall>) : ViewModel() {
    private val _stalls = MutableStateFlow<List<Stall>>(emptyList())
    val stalls: StateFlow<List<Stall>> get() = _stalls
    private val _selectedStall = MutableStateFlow<Stall?>(null)
    val selectedStall: StateFlow<Stall?> = _selectedStall

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

    // Get stall by stallId
    fun getStallById(stallId: Int?) = viewModelScope.launch {
        if (stallId != null) {
            _selectedStall.value = stallStorage.get { it.getIdentifier() == stallId }.first()
        } else {
            _selectedStall.value = null
        }
    }

    // Function to add a review to a stall
    fun addReviewToStall(stallId: Int, review: Review) = viewModelScope.launch {
        val stall = stallStorage.get { it.getIdentifier() == stallId }.firstOrNull()
        if (stall != null) {
            stall.reviews.add(review)
            stallStorage.edit(stallId, stall).collect {}
            stallStorage.getAll().catch { Log.e("STALL_VIEW_MODEL", it.toString()) }
                .collect { _stalls.emit(it) }
        }
    }

    // Deletes all stalls in the datastore
    fun deleteAll() = viewModelScope.launch {
        stallStorage.deleteAll().collect {}
    }

    // Loads default stalls if there are none in the datastore all stalls in the datastore
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

