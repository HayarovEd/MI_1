package lo.zaemtoperson.gola.data
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.data.Utils.Companion.log

class SecondFirebaseMessagingService: FirebaseMessagingService() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    @SuppressLint("MissingPermission", "NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification(message: RemoteMessage) {
        Log.d("BBBBBB", "remoteMessage $message")
        if (message.notification != null) {
            val title = message.notification?.title.toString()
            Log.d("BBBBBB", "title $title")
            val body = message.notification?.body.toString()
            Log.d("BBBBBB", "body $body")
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
            val notification = Notification.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(android.R.drawable.ic_menu_manage)
                .setColor(getColor(R.color.back))
                .setAutoCancel(true)
                .build()
            NotificationManagerCompat.from(this).notify(1, notification)
        }
        if (message.data.isNotEmpty()) {
            val bookId = message.data[LINK]
            log(bookId)
        }
    }

    override fun onNewToken(token: String) {
        log(token)
    }
}