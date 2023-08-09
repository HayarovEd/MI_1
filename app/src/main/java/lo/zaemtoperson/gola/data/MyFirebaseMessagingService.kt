package lo.zaemtoperson.gola.data

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import lo.zaemtoperson.gola.domain.model.Notification

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("BBBBBB", "Refreshed token: $token")
        //sendRegistrationToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val icon = remoteMessage.notification?.icon
        val minImage = remoteMessage.notification?.link
        val title = remoteMessage.notification?.icon
        val message = remoteMessage.notification?.body
        val image = remoteMessage.notification?.imageUrl
        Log.d("BBBBBB", "remoteMessage $remoteMessage")
        NotificationData.notificationLiveData.postValue(
            Notification(
            icon =icon,
            minImage = minImage,
            title = title,
            message = message,
            image = image
        )
        )
    }

    object NotificationData {
        val notificationLiveData = MediatorLiveData<Notification>()
    }
}