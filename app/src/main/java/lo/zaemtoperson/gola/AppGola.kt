package lo.zaemtoperson.gola

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.deeplink.DeepLinkListener
import com.appsflyer.deeplink.DeepLinkResult
import com.google.firebase.messaging.FirebaseMessaging
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.APPS_FLYER
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.MY_TRACKER
import javax.inject.Inject


//import pro.userx.UserX


@HiltAndroidApp
class AppGola @Inject constructor(): Application() {
    var appsFlayer = ""
    var myTracker = ""
    override fun onCreate() {
        super.onCreate()
        AppsFlyerLib.getInstance().subscribeForDeepLink(object : DeepLinkListener {
            override fun onDeepLinking(deepLinkResult: DeepLinkResult) {
                when (deepLinkResult.status) {
                    DeepLinkResult.Status.FOUND -> {
                        Log.d(
                            "ASDFGH","Deep link found"
                        )
                    }
                    DeepLinkResult.Status.NOT_FOUND -> {
                        Log.d(
                            "ASDFGH","Deep link not found"
                        )
                        return
                    }
                    else -> {
                        val dlError = deepLinkResult.error
                        Log.d(
                            "ASDFGH","There was an error getting Deep Link data: $dlError"
                        )
                        return
                    }
                }
                val deepLinkObj = deepLinkResult.deepLink
                try {
                    Log.d(
                        "ASDFGH","The DeepLink data is: $deepLinkObj"
                    )
                } catch (e: Exception) {
                    Log.d(
                        "ASDFGH","DeepLink data came back null"
                    )
                    return
                }
                if (deepLinkObj.isDeferred == true) {
                    Log.d("ASDFGH", "This is a deferred deep link");
                } else {
                    Log.d("ASDFGH", "This is a direct deep link");
                }

                try {
                    val fruitName = deepLinkObj.deepLinkValue
                    appsFlayer = fruitName?:""
                    Log.d("ASDFGH", "The DeepLink will route to: $fruitName")
                } catch (e:Exception) {
                    Log.d("ASDFGH", "There's been an error: $e");
                    return;
                }
            }
        })
        AppsFlyerLib.getInstance().init(APPS_FLYER, null, this)
        AppsFlyerLib.getInstance().start(this)
        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()
        //val intent = Intent()
        //myTarcker = MyTracker.handleDeeplink(intent)?:""

        MyTracker.setAttributionListener {
            myTracker = it.deeplink
            Log.d("ASDFGH", "myTracker app $myTracker")
        }
        MyTracker.initTracker(MY_TRACKER, this)
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}