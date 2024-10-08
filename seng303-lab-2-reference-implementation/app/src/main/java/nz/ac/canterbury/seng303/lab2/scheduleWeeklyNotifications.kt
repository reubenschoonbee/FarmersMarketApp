package nz.ac.canterbury.seng303.lab2

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nz.ac.canterbury.seng303.lab2.models.Market
import nz.ac.canterbury.seng303.lab2.viewmodels.MarketViewModel
import java.util.concurrent.TimeUnit
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.google.gson.Gson
import androidx.work.Data

fun scheduleWeeklyNotifications(context: Context, marketViewModel: MarketViewModel) {
    // Use WorkManager to schedule notifications
    marketViewModel.getMarkets()
    val markets: List<Market> by marketViewModel.markets.collectAsState(emptyList())
    val marketDataString = serializeMarkets(markets)

    // Prepare Data for the Worker
    val data = Data.Builder()
        .putString("market_data", marketDataString)
        .build()

    // Create a WorkRequest with the data
    val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(7, TimeUnit.DAYS)
        .setInputData(data)
        .setInitialDelay(calculateInitialDelay(), TimeUnit.MILLISECONDS)
        .build()

    // Enqueue the WorkRequest
    WorkManager.getInstance(context).enqueue(workRequest)
}

// Serialize market data to JSON
private fun serializeMarkets(markets: List<Market>): String {
    val gson = Gson()
    return gson.toJson(markets)
}
// Calculate the initial delay based on market opening times
private fun calculateInitialDelay(): Long {
    // Logic to calculate the delay until the next market opening
    // For example, if Market 1 opens in 24 hours, return 24 hours in milliseconds
    return 24 * 60 * 60 * 1000 // Example delay, replace with your logic
}
