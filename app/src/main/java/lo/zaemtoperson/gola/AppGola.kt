package lo.zaemtoperson.gola

import android.app.Application
import android.content.Context
import android.content.Intent
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
import lo.zaemtoperson.gola.data.SHARED_APPSFLYER_SUB
import lo.zaemtoperson.gola.data.SHARED_DATA
import java.lang.StringBuilder


//import pro.userx.UserX


@HiltAndroidApp
class AppGola: Application() {
    var appsFlayer = ""
    var myTarcker = ""
    override fun onCreate() {
        super.onCreate()
        val sharedPref = this.getSharedPreferences(SHARED_DATA, Context.MODE_PRIVATE)
        MyTracker.initTracker(MY_TRACKER, this)
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                val temp = StringBuilder("{")
                conversionData.forEach{
                    if (it!=conversionData.entries.last()) {
                        temp.append("\"${it.key}\": \"${it.value}\", ")
                    } else {
                        temp.append("\"${it.key}\": \"${it.value}\"")
                    }
                    sharedPref.edit().putString(SHARED_APPSFLYER_SUB, it.value.toString()).apply()
                }
                temp.append("}")
                appsFlayer = temp.toString()
            }

            override fun onConversionDataFail(error: String?) {
                println("SDFRD conversion error $error")
                //sharedPref.edit().putString(SHARED_APPSFLYER_SUB, error).apply()
            }

            override fun onAppOpenAttribution(attributionData: MutableMap<String, String>?) {
                attributionData?.forEach{
                    println("SDFRD attribution key ${it.key} valur ${it.value}")
                    //sharedPref.edit().putString(SHARED_APPSFLYER_SUB, it.value).apply()
                }
            }

            override fun onAttributionFailure(error: String?) {
                println("SDFRD attribution error $error")
                //sharedPref.edit().putString(SHARED_APPSFLYER_SUB, error).apply()
            }
        }
        AppsFlyerLib.getInstance().init(APPS_FLYER, conversionDataListener, this)
        AppsFlyerLib.getInstance().start(this)
        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()
        val intent = Intent()
        myTarcker = MyTracker.handleDeeplink(intent)?:""
        Log.d("ASDFGH", "myTarcker $myTarcker")
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
        //UserX.init(USER_X)
    }


}