package nz.ac.canterbury.seng303.lab2
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import nz.ac.canterbury.seng303.lab2.models.Market
import java.util.concurrent.TimeUnit
import com.google.gson.Gson
import androidx.work.Data
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.content.Context

fun scheduleWeeklyNotifications(context: Context, markets: List<Market>, value: Boolean) {
    val workManager = WorkManager.getInstance(context)

    markets.forEach { market ->
        // Serialize the market data
        val marketDataString = serializeMarkets(listOf(market))

        // Prepare Data for the Worker
        val data = Data.Builder()
            .putString("market_data", marketDataString)
            .build()

        // Calculate the delay for this market
        val delayInMillis = calculateInitialDelay(market)

        if (delayInMillis < TimeUnit.HOURS.toMillis(24)) {
            // Case where market is opening in less than 24 hours, send a notification in 30 seconds
            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInputData(data)
                .setInitialDelay(1, TimeUnit.SECONDS)
                .build()

            // Enqueue the OneTimeWorkRequest for immediate notification
            workManager.enqueue(oneTimeWorkRequest)

        } else {
            // Case where market is opening in more than 24 hours, send the notification 24 hours before opening
            val periodicWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(7, TimeUnit.DAYS)
                .setInputData(data)
                .setInitialDelay(delayInMillis - TimeUnit.HOURS.toMillis(24), TimeUnit.MILLISECONDS)
                .build()

            // Enqueue the PeriodicWorkRequest
            workManager.enqueue(periodicWorkRequest)
        }
    }
}

// Helper function to calculate the initial delay
fun calculateInitialDelay(market: Market): Long {
    val calendar = Calendar.getInstance()

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

    // Get the current day of the week
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

    // Calculate the delay in milliseconds from the current time to the next market opening time
    val delayInMillis = calendar.timeInMillis - System.currentTimeMillis()

    // If the market opens in less than 24 hours, return the time until the opening
    return if (delayInMillis < TimeUnit.HOURS.toMillis(24)) {
        delayInMillis
    } else {
        delayInMillis - TimeUnit.HOURS.toMillis(24)
    }
}

// Serialize market data to JSON
private fun serializeMarkets(markets: List<Market>): String {
    val gson = Gson()
    return gson.toJson(markets)
}
