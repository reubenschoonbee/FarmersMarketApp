package nz.ac.canterbury.seng303.lab2

import android.content.Context
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import nz.ac.canterbury.seng303.lab2.models.Market

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
    private val gson = Gson()

    override fun doWork(): Result {
        // Retrieve the market data from input
        val marketDataString = inputData.getString("market_data") ?: return Result.failure()

        // Deserialize market data
        val markets = deserializeMarkets(marketDataString)

        // Create the notification handler
        val notificationHandler = NotificationHandler(applicationContext)

        // Show notifications for each market
        markets.forEach { market ->
            // Here, customize how you want to show the notification
            notificationHandler.showSimpleNotification(market.name, market.openTimes)
        }

        return Result.success()
    }

    private fun deserializeMarkets(dataString: String): List<Market> {
        val type = object : TypeToken<List<Market>>() {}.type
        return gson.fromJson(dataString, type)
    }
}
