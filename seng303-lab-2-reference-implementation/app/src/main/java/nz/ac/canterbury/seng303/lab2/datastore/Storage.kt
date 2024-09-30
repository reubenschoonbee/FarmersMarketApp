package nz.ac.canterbury.seng303.lab2.datastore

import kotlinx.coroutines.flow.Flow
import nz.ac.canterbury.seng303.lab2.models.Identifiable

interface Storage<T> where T : Identifiable {
    fun insert(data: T): Flow<Int>
    fun insertAll(data: List<Identifiable>): Flow<Int>
    fun getAll(marketId: Int? = null): Flow<List<T>>
    fun delete(identifier: Int): Flow<Int>
    fun edit(identifier: Int, data: T): Flow<Int>
    fun get(where: (T) -> Boolean): Flow<T>
}