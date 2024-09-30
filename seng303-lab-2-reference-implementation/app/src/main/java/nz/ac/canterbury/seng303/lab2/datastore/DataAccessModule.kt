package nz.ac.canterbury.seng303.lab2.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.FlowPreview
import nz.ac.canterbury.seng303.lab2.models.Market1Stalls
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "nz.ac.canterbury.seng303.lab1.shared.preferences")

@FlowPreview
val dataAccessModule = module {
    single<Storage<Market1Stalls>> {
        PersistentStorage(
            gson = get(),
            type = object: TypeToken<List<Market1Stalls>>(){}.type,
            preferenceKey = stringPreferencesKey("stall"),
            dataStore = androidContext().dataStore
        )
    }

    single { Gson() }

    viewModel {
        StallViewModel(
            stallStorage = get()
        )
    }
}




