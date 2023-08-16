package lo.zaemtoperson.gola

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.APPS_FLYER
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.MY_TRACKER


//import pro.userx.UserX


@HiltAndroidApp
class AppGola: Application() {
    var appsFlayer = ""
    var myTarcker = ""
    override fun onCreate() {
        super.onCreate()
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                appsFlayer = conversionData.entries.joinToString(separator = ", ") { "${it.key}=${it.value}" }
                Log.d("ASDFGH", "temp -  $appsFlayer")
            }

            override fun onConversionDataFail(error: String?) {
                println("SDFRD conversion error $error")
            }

            override fun onAppOpenAttribution(attributionData: MutableMap<String, String>?) {
                attributionData?.forEach{
                    println("SDFRD attribution key ${it.key} valur ${it.value}")
                }
            }

            override fun onAttributionFailure(error: String?) {
                println("SDFRD attribution error $error")
            }
        }
        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)
        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()
        //val intent = Intent()
        //myTarcker = MyTracker.handleDeeplink(intent)?:""

        MyTracker.setAttributionListener {
            myTarcker = it.deeplink
            Log.d("ASDFGH", "myTarcker app $myTarcker")
        }
        MyTracker.initTracker(MY_TRACKER, this)
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }


}