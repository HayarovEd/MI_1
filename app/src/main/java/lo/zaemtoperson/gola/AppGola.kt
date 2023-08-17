package lo.zaemtoperson.gola

import android.app.Application
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import lo.zaemtoperson.gola.data.APP_METRICA
import lo.zaemtoperson.gola.data.MY_TRACKER


//import pro.userx.UserX


@HiltAndroidApp
class AppGola : Application() {
    override fun onCreate() {
        super.onCreate()

        val config = YandexMetricaConfig.newConfigBuilder(APP_METRICA).build()


        MyTracker.initTracker(MY_TRACKER, this)
        YandexMetrica.activate(applicationContext, config)
        YandexMetrica.enableActivityAutoTracking(this)
    }
}