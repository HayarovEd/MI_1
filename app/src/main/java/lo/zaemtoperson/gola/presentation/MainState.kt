package lo.zaemtoperson.gola.presentation

data class MainState(
    val appMetrica: String = "",
    val colorFb: String = "",
    val sim: String? = null,
    val instanceId: String? = null,
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
)
