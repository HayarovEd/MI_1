package lo.zaemtoperson.gola.presentation

import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.basedto.BaseDto

data class MainState(
    val appMetrica: String = "",
    val colorFb: String = "",
    val sim: String? = null,
    val instanceIdMyTracker: String? = null,
    val instanceIdAppsFlyer: String? = null,
    val isRoot: Boolean = false,
    val locale: String = "",
    val deviceId: String? = null,
    val fireBaseToken: String? = null,
    val gaid: String? = null,
    val versionApplication: String? = null,
    val trackerDeeplink: String? = null,
    val appsFlyerDeeplink: String? = null,
    val isConnectInternet:Boolean = false,
    val message: String = "",
    val affsub1Unswer: String = "",
    val affsub2Unswer: String = "",
    val affsub3Unswer: String = "",
    val affsub5Unswer: String = "",
    val dbData: BaseDto? = null,
    val statusApplication: StatusApplication = StatusApplication.Mock
)
