package lo.zaemtoperson.gola.data

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build.VERSION_CODES
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification
import lo.zaemtoperson.gola.R

class SecondFirebaseMessagingService: FirebaseMessagingService() {
    @RequiresApi(VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    @SuppressLint("MissingPermission", "NewApi")
    @RequiresApi(VERSION_CODES.M)
    private fun sendNotification(message: RemoteMessage) {
        if (message.notification != null) {
            val title = message.notification?.title.toString()
            val body = message.notification?.body.toString()

            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            val notification = Notificationю
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.ic_menu_manage)
                .setColor(getColor(R.color.back))
                .setAutoCancel(true)
                .build()
            NotificationManagerCompat.from(this).notify(1, notification)
        }
        if (message.data.isNotEmpty()) {
            val bookId = message.data[BOOK_ID]
            log(bookId)
        }
    }

    override fun onNewToken(token: String) {
        log(token)
    }
}