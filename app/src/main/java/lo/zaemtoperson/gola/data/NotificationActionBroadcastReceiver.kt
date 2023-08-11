package lo.zaemtoperson.gola.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.RemoteInput
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log

class NotificationActionBroadcastReceiver : BroadcastReceiver() {
    companion object {
        const val CHANNEL_ID_EXTRA = "channel_id"

        const val FIRST_ACTION = "first"
        const val FIRST_ACTION_EXTRA = "first_extra"

        const val TEXT_ACTION = "text"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        val channelId = intent?.extras?.getInt(CHANNEL_ID_EXTRA) ?: 0
        val firstActionExtra = intent?.extras?.getString(FIRST_ACTION_EXTRA)

        when(intent?.action) {
            FIRST_ACTION -> {
                if (firstActionExtra != null) {
                    context?.let {
                        val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        val pendingIntent = PendingIntent.getActivity(
                            it, 0, intent,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        val newNotification = it.setNotification(
                            channelId = CHANNEL_ID,
                            title = "Action",
                            body = "Data is, $firstActionExtra",
                            groupId = null,
                            pendingIntent = pendingIntent
                        )

                        notificationManager.notify(channelId, newNotification.build())
                    }
                }
            }
            TEXT_ACTION -> {
                if (getMessageText(intent) != null) {
                    context?.let {
                        val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

                        val pendingIntent = PendingIntent.getActivity(
                            it, 0, intent,
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else PendingIntent.FLAG_UPDATE_CURRENT
                        )
                        val newNotification = it.setNotification(
                            channelId = CHANNEL_ID,
                            title = "Success, message sent!",
                            body = getMessageText(intent).toString(),
                            groupId = null,
                            pendingIntent = pendingIntent
                        )

                        notificationManager.notify(channelId, newNotification.build())
                    }
                }
            }
        }
    }

    private fun getMessageText(intent: Intent): CharSequence? {
        return RemoteInput.getResultsFromIntent(intent)?.getCharSequence(TEXT_ACTION)
    }
}