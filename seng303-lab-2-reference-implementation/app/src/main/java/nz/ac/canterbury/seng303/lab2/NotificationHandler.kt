package nz.ac.canterbury.seng303.lab2

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class NotificationHandler(private val context: Context) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "notification_channel_id"

    // SIMPLE NOTIFICATION
    fun showSimpleNotification(marketName: String, openingTimes: String) {
        // Use CoroutineScope to launch in a background thread
        CoroutineScope(Dispatchers.Main).launch {
            // Create the notification
            val notification = NotificationCompat.Builder(context, notificationChannelID)
                .setContentTitle("Market Opening Reminder")
                .setContentText("The market '$marketName' opens at: $openingTimes")
                .setSmallIcon(R.drawable.round_notifications_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .build()  // Finalizes the creation

            // Notify the user
            notificationManager.notify(Random.nextInt(), notification)
        }
    }
}
