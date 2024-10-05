package nz.ac.canterbury.seng303.lab2.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import nz.ac.canterbury.seng303.lab2.models.Identifiable
import nz.ac.canterbury.seng303.lab2.models.Market1Stalls
import nz.ac.canterbury.seng303.lab2.models.Market2Stalls
import java.lang.reflect.Type

class PersistentStorage<T>(
    private val gson: Gson,
    private val type: Type,
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<String>
) : Storage <T> where T: Identifiable {

    override fun insert(data: T): Flow<Int> {
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            cachedDataClone.add(data)
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                emit(OPERATION_SUCCESS)
            }
        }
    }

    override fun insertAll(data: List<Identifiable>): Flow<Int> {// Ensure this uses the generic type T
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            data.forEach { item ->
                if (item is Market1Stalls || item is Market2Stalls) { // Adjust based on your types
                    cachedDataClone.add(item as T) // Add the item safely
                }
            }
            dataStore.edit {
                val jsonString = gson.toJson(cachedDataClone, type)
                it[preferenceKey] = jsonString
                emit(OPERATION_SUCCESS)
            }
        }
    }

    override fun getAll(marketId: Int?): Flow<List<T>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: EMPTY_JSON_STRING

            // Deserialize based on marketId
            val elements: List<T> = when (marketId) {
                1 -> gson.fromJson<List<Market1Stalls>>(jsonString, object : TypeToken<List<Market1Stalls>>() {}.type) as List<T>
                2 -> gson.fromJson<List<Market2Stalls>>(jsonString, object : TypeToken<List<Market2Stalls>>() {}.type) as List<T>
                else -> emptyList() // Return empty if marketId is not valid
            }

            // Filter elements based on marketId if provided
            elements.filter { element ->
                if (marketId != null) {
                    (element as? Identifiable)?.getIdentifier() == marketId // Use Identifiable for filtering
                } else {
                    true // Return all if no marketId is provided
                }
            }
        }
    }

    override fun getStallByName(marketId: Int, stallName: String): Flow<T?> {
        return getAll(marketId).map { stalls ->
            stalls.firstOrNull { stall ->
                // Check if the stall name matches (ignoring case sensitivity)
                (stall as? Market1Stalls)?.name.equals(stallName, ignoreCase = true) ||
                        (stall as? Market2Stalls)?.name.equals(stallName, ignoreCase = true)
            }
        }
    }




    override fun getCategories(marketId: Int): Flow<List<String>> {
        return dataStore.data.map { preferences ->
            val jsonString = preferences[preferenceKey] ?: EMPTY_JSON_STRING

            // Deserialize based on marketId
            val categories: List<String> = when (marketId) {
                1 -> {
                    val market1Stalls: List<Market1Stalls> = gson.fromJson(jsonString, object : TypeToken<List<Market1Stalls>>() {}.type) ?: emptyList()
                    market1Stalls.map { it.category }
                }
                2 -> {
                    val market2Stalls: List<Market2Stalls> = gson.fromJson(jsonString, object : TypeToken<List<Market2Stalls>>() {}.type) ?: emptyList()
                    market2Stalls.map { it.category }
                }
                else -> emptyList() // Handle invalid marketId if needed
            }

            // Return distinct categories
            categories.distinct()
        }
    }


    override fun edit(identifier: Int, data: T): Flow<Int> {
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            val index = cachedDataClone.indexOfFirst { it.getIdentifier() == identifier }
            if (index != -1) {
                cachedDataClone[index] = data  // Update the item with the new data
                dataStore.edit {  // Save the updated list
                    val jsonString = gson.toJson(cachedDataClone, type)
                    it[preferenceKey] = jsonString
                    emit(OPERATION_SUCCESS)
                }
            } else {
                emit(OPERATION_FAILURE)  // Handle the case when the item with the given identifier is not found
            }
        }
    }

    override fun get(where: (T) -> Boolean): Flow<T> {
        return flow {
            val data = getAll().first().first(where)
            emit(data)
        }
    }

    override fun delete(identifier: Int): Flow<Int> {
        return flow {
            val cachedDataClone = getAll().first().toMutableList()
            val updatedData = cachedDataClone.filterNot { it.getIdentifier() == identifier }
            dataStore.edit {
                val jsonString = gson.toJson(updatedData, type)
                it[preferenceKey] = jsonString
                emit(OPERATION_SUCCESS)
            }
        }
    }

    companion object {
        private const val OPERATION_SUCCESS = 1
        private const val OPERATION_FAILURE = -1
        private const val EMPTY_JSON_STRING = "[]"
    }
}
