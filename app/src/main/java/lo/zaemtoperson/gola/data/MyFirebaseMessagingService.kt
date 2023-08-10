package lo.zaemtoperson.gola.data

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import lo.zaemtoperson.gola.R
import lo.zaemtoperson.gola.domain.model.Notification
import lo.zaemtoperson.gola.presentation.MainActivity


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("BBBBBB", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("BBBBBB", "remoteMessage $remoteMessage")


        if (remoteMessage.data.isNotEmpty()) {
            val message = remoteMessage.data[LINK]
            val subString = message?.split("/")

            val bundle = Bundle().apply {
                putString(KEY1, subString?.first())
                putInt(KEY2, subString?.last()?.toInt()?:0)
            }
            val intent = Intent().apply {
                action = SAVED_SETTINGS
                putExtra(BUNDLE, bundle)
            }
            sendBroadcast(intent)
        }

    }

    object NotificationData {
        val notificationLiveData = MediatorLiveData<Notification>()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun generateNotification (title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(
            applicationContext, "channelId"
        )
            .setSmallIcon(R.drawable.ic_stat_ic_notification)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)
        val asd = builder.setContent(getRemoteView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q) {
            val notificationChannel = NotificationChannel("channelId", "lo.zaemtoperson.gola.data", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        notificationManager.notify(0, asd.build())
    }

    @SuppressLint("RemoteViewLayout")
    private fun getRemoteView(title: String, message: String): RemoteViews? {
        val remoteView = RemoteViews ("lo.zaemtoperson.gola.data", R.layout.notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.message, message)
        remoteView.setImageViewResource(R.id.logo, R.drawable.ic_stat_ic_notification)

        return remoteView
    }
}