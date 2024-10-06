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
import nz.ac.canterbury.seng303.lab2.models.Market


class MarketViewModel( private val marketStorage: Storage<Market>): ViewModel() {
    private val _markets = MutableStateFlow<List<Market>>(emptyList())
    val markets: StateFlow<List<Market>> get() = _markets

    fun getMarkets() = viewModelScope.launch {
        marketStorage.getAll().catch { Log.e("MARKET_VIEW_MODEL", it.toString()) }
            .collect { _markets.emit(it) }
    }

    // Deletes all markets in the datastore
    fun deleteAll() = viewModelScope.launch {
        marketStorage.deleteAll().collect {}
    }


    fun loadDefaultNotesIfNoneExist() = viewModelScope.launch {
        val currentMarkets = marketStorage.getAll().first()
        if (currentMarkets.isEmpty()) {
            Log.d("MARKET_VIEW_MODEL", "Adding Test Markets...")

            marketStorage.insertAll(Market.getMarkets()).catch {
                Log.w("marketStorage", "Could not insert test Markets")
            }.collect {
                Log.d("MARKET_VIEW_MODEL", "Test Markets inserted successfully")
                _markets.emit(Market.getMarkets())
            }
        }
    }

}