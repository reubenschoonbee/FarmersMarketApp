package nz.ac.canterbury.seng303.lab2.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.FlowPreview
import nz.ac.canterbury.seng303.lab2.models.Market
import nz.ac.canterbury.seng303.lab2.models.Stall
import nz.ac.canterbury.seng303.lab2.viewmodels.MarketViewModel
import nz.ac.canterbury.seng303.lab2.viewmodels.StallViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "nz.ac.canterbury.seng303.ass2.shared.preferences"
)

@FlowPreview
val dataAccessModule = module {
    single { Gson() }

    single<Storage<Market>>(named("marketStorage")) {
        PersistentStorage(
            gson = get(),
            type = object : TypeToken<List<Market>>() {}.type,
            preferenceKey = stringPreferencesKey("market"),
            dataStore = androidContext().dataStore
        )
    }

    single<Storage<Stall>>(named("stallStorage")) {
        PersistentStorage(
            gson = get(),
            type = object : TypeToken<List<Stall>>() {}.type,
            preferenceKey = stringPreferencesKey("stall"),
            dataStore = androidContext().dataStore
        )
    }

    viewModel {
        MarketViewModel(
            marketStorage = get(named("marketStorage"))
        )
    }

    viewModel {
        StallViewModel(
            stallStorage = get(named("stallStorage"))
        )
    }
}
