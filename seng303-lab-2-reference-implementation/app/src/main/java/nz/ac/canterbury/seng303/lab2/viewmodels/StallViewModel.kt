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
import nz.ac.canterbury.seng303.farmersmarket.models.Market1Stalls

class StallViewModel(private val stallStorage: Storage<Market1Stalls>) : ViewModel() {
    private val _selectedStall = MutableStateFlow<Market1Stalls?>(null)
    val selectedStall: StateFlow<Market1Stalls?> = _selectedStall
    private val _stalls = MutableStateFlow<List<Market1Stalls>>(emptyList())
    val stalls: StateFlow<List<Market1Stalls>> get() = _stalls

    fun getStallById(stallId: Int?) = viewModelScope.launch {
        if (stallId != null) {
            _selectedStall.value = stallStorage.get { it.getIdentifier() == stallId }.first()
        } else {
            _selectedStall.value = null
        }
    }

    fun getStalls(marketId: Int?) = viewModelScope.launch {
        stallStorage.getAll(marketId).catch { Log.e("STALL_VIEW_MODEL", it.toString()) }.collect { _stalls.emit(it) }
    }

        fun loadDefaultStallsIfNoneExist(marketId: Int) = viewModelScope.launch {
            val currentStalls = stallStorage.getAll(marketId).first()
            if (currentStalls.isEmpty()) {
                Log.d("STALL_VIEW_MODEL", "Inserting default stalls...")
                stallStorage.insertAll(Market1Stalls.getStalls()).catch {
                    Log.w("STALL_VIEW_MODEL", "Could not insert default stalls")
                }.collect {
                    Log.d("STALL_VIEW_MODEL", "Default stalls inserted successfully")
                    _stalls.emit(Market1Stalls.getStalls())
                }
            }
        }



}
