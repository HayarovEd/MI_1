package com.fast.zai.loans.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.huawei.hms.push.HmsMessageService
import com.huawei.hms.push.RemoteMessage
import com.fast.zai.loans.R
import com.fast.zai.loans.presentation.MainActivity


class MyPushService: HmsMessageService() {

    override fun onNewToken(token: String?) {
        val context = applicationContext
        val sharedPref = context.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        Log.d("PushDemoLog", "have received refresh token:$token")
        sharedPref.edit().putString(SHARED_FIREBASE_TOKEN, token).apply()
        /*val intent = Intent("custom")
        intent.putExtra("fb", token)
        sendBroadcast(intent)*/
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        if (remoteMessage != null) {
            if (remoteMessage.data.isNotEmpty()) {
                handleDataMessage(remoteMessage.dataOfMap)
            }
        }
    }

    private fun handleDataMessage(data: Map<String, String>) {
        Log.d("PushDemoLog", "data push:$data")
        val link = data[LINK]
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val requestCode = 0
        val pendingIntent = PendingIntent.getActivity(
            this,
            requestCode,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("FCM Message")
            .setContentText(link)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT,
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationId = 0
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}