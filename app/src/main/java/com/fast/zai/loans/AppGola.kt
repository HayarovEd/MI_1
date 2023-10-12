package com.fast.zai.loans

import android.app.Application
import com.my.tracker.MyTracker
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig
import dagger.hilt.android.HiltAndroidApp
import com.fast.zai.loans.data.APP_METRICA
import com.fast.zai.loans.data.MY_TRACKER


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