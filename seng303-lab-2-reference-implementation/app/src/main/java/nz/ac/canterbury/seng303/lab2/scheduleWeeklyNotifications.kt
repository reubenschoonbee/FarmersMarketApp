package nz.ac.canterbury.seng303.lab2

import android.content.Context
import android.util.Log
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nz.ac.canterbury.seng303.lab2.models.Market
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import androidx.work.Data
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


fun scheduleWeeklyNotifications(context: Context, markets: List<Market>) {
    // Use WorkManager to schedule notifications

    val workManager = WorkManager.getInstance(context)

    markets.forEach { market ->
        // Serialize the market data
        val marketDataString = serializeMarkets(listOf(market))

        // Prepare Data for the Worker
        val data = Data.Builder()
            .putString("market_data", marketDataString)
            .build()

        // Calculate the delay for this market
        val initialDelay = calculateInitialDelay(market)

        // Create a WorkRequest with the calculated delay
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(7, TimeUnit.DAYS)
            .setInputData(data)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        // Enqueue the WorkRequest
        workManager.enqueue(workRequest)
    }
}
// Serialize market data to JSON
private fun serializeMarkets(markets: List<Market>): String {
    val gson = Gson()
    return gson.toJson(markets)
}
// Calculate the initial delay based on market opening times

// Helper function to calculate the delay
fun calculateInitialDelay(market: Market): Long {
    val calendar = Calendar.getInstance()
    Log.d("weekly notification", "${market.openTimesForCalender}")
    // Parse the market's open day and time (e.g., "Sunday" and "09:00")
    val openDay = market.openTimesForCalender.split(",")[0].trim() // Extract the day (e.g., "Sunday")
    val openTime = market.openTimesForCalender.split(",")[1].trim() // Extract the time (e.g., "09:00")

    // Get the day of the week corresponding to openDay (e.g., Calendar.SUNDAY)
    val dayOfWeek = when (openDay.lowercase(Locale.getDefault())) {
        "sunday" -> Calendar.SUNDAY
        "monday" -> Calendar.MONDAY
        "tuesday" -> Calendar.TUESDAY
        "wednesday" -> Calendar.WEDNESDAY
        "thursday" -> Calendar.THURSDAY
        "friday" -> Calendar.FRIDAY
        "saturday" -> Calendar.SATURDAY
        else -> throw IllegalArgumentException("Invalid day: $openDay")
    }

    // Set the calendar to the next occurrence of the market's open day and time
    val currentDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val daysUntilNextOpen = if (dayOfWeek >= currentDayOfWeek) {
        dayOfWeek - currentDayOfWeek
    } else {
        7 - (currentDayOfWeek - dayOfWeek)
    }

    // Set the opening time (e.g., 09:00)
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val openTimeDate = timeFormat.parse(openTime)

    val openHour = Calendar.getInstance().apply {
        time = openTimeDate
    }.get(Calendar.HOUR_OF_DAY)
    val openMinute = Calendar.getInstance().apply {
        time = openTimeDate
    }.get(Calendar.MINUTE)

    // Set the calendar to the next market opening time
    calendar.add(Calendar.DAY_OF_YEAR, daysUntilNextOpen)
    calendar.set(Calendar.HOUR_OF_DAY, openHour)
    calendar.set(Calendar.MINUTE, openMinute)
    calendar.set(Calendar.SECOND, 0)

    // Subtract 24 hours for the notification time
    calendar.add(Calendar.HOUR_OF_DAY, -24)

    // Calculate the delay from the current time
    val delayInMillis = calendar.timeInMillis - System.currentTimeMillis()
    // Edge case: if delay is negative (market opens in less than 24 hours), send a notification after a minute
    Log.d("Notification scheduler", "$delayInMillis" )
    if (delayInMillis < 0) {
        return TimeUnit.SECONDS.toMillis(30) // Schedule for 1 minute later
    }
    return TimeUnit.MILLISECONDS.toMillis(delayInMillis)
}
