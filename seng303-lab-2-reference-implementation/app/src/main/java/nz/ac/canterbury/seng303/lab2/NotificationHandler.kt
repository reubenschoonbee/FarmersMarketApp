package nz.ac.canterbury.seng303.lab2

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_IMMUTABLE
        )
        // Use CoroutineScope to launch in a background thread
        CoroutineScope(Dispatchers.Main).launch {
            // Create the notification
            val notification = NotificationCompat.Builder(context, notificationChannelID)
                .setContentTitle("Market Opening Reminder")
                .setContentText("The market '$marketName' opens at: $openingTimes")
                .setSmallIcon(R.drawable.round_notifications_24)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()  // Finalizes the creation

            // Notify the user
            notificationManager.notify(Random.nextInt(), notification)
        }
    }
}
