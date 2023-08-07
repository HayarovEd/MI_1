package lo.zaemtoperson.gola

import android.app.Application
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.my.tracker.MyTracker
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.APPS_FLYER
import lo.zaemtoperson.gola.data.MY_TRACKER

//import pro.userx.UserX


@HiltAndroidApp
class AppGola: Application() {
    override fun onCreate() {
        super.onCreate()
        MyTracker.initTracker(MY_TRACKER, this)
        val conversionDataListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                conversionData.forEach{
                    println("SDFRD conversion key ${it.key} valur ${it.value}")
                }
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
        //UserX.init(USER_X)
    }


}