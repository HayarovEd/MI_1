package lo.zaemtoperson.gola.data
import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.data.Utils.Companion.log
import lo.zaemtoperson.gola.presentation.MainActivity

class SecondFirebaseMessagingService: FirebaseMessagingService() {

    companion object {
        const val CHANNEL_NAME = "Test Notification"
        const val GROUP_NAME = "Test Group Notification"
        const val GROUP_ID = "test.notification"

        const val PATH_EXTRA = "path"
        const val DATA_EXTRA = "data"
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        sendNotification(message)
    }

    @SuppressLint("MissingPermission", "NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    private fun sendNotification(remoteMessage: RemoteMessage) {
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("Test", "Message data payload: ${remoteMessage.data}")
        }
        remoteMessage.notification?.let {
            Log.d("Test", "Message Notification Body: ${it.body}")
            sendNotification(it.title, it.body, remoteMessage.data)
        }
    }

    override fun onNewToken(token: String) {
        log(token)
    }

    private fun sendNotification(
        title: String?,
        messageBody: String?,
        data: Map<String, String>
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP

        for (i in 0 until data.size) {
            val key = data.keys.toList()[i]
            val value = data.values.toList()[i]
            intent.putExtra(key, value)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = CHANNEL_ID
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = setNotification(
            channelId,
            title,
            messageBody,
            NOTIFICATION_ID,
            pendingIntent
        )

        val groupNotification = setGroupNotification(
            channelId = channelId,
            groupId = NOTIFICATION_ID,
            groupSummary = true,
            summaryText = "$title $messageBody",
        )

        //ID of notification
        notificationManager.notify(System.currentTimeMillis().toInt(), notification.build())
        notificationManager.notify(0, groupNotification)
    }
}

fun Context.setNotification(
    channelId: String,
    title: String?,
    body: String?,
    groupId: String?,
    pendingIntent: PendingIntent,
): NotificationCompat.Builder {
    val notification = NotificationCompat.Builder(this, channelId)
        .setSmallIcon(R.drawable.ic_stat_ic_notification)
        .setContentTitle(title)
        .setContentText(body)
        .setAutoCancel(true)
        .setGroupSummary(false)

    if (groupId != null)
        notification.setGroup(groupId)

    notification.setContentIntent(pendingIntent)

    return notification
}

fun Context.setGroupNotification(
    channelId: String,
    groupId: String,
    groupSummary: Boolean,
    summaryText: String
): Notification = NotificationCompat.Builder(this, channelId)
    .setSmallIcon(R.drawable.ic_stat_ic_notification)
    .setStyle(
        NotificationCompat.InboxStyle()
            .setSummaryText(summaryText)
    )
    .setGroup(groupId)
    .setGroupSummary(groupSummary)
    .setAutoCancel(true)
    .build()