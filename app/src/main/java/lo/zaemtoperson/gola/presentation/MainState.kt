package lo.zaemtoperson.gola.presentation

data class MainState(
    val appMetrica: String = "n",
    val sim: String? = "n",
    val instanceId: String = "n",
    val isRoot: Boolean = false,
    val locale: String = "n",
    val deviceId: String? = "n",
    val fireBaseToken: String? = "n",
    val gaid: String? = "n",
    val versionApplication: String? = "n",
    val trackerDeeplink: String? = "n",
    val appsFlyerDeeplink: String? = "n",
)
