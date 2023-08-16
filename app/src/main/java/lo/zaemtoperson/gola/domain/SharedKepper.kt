package lo.zaemtoperson.gola.domain

interface SharedKepper {

    fun getFireBaseToken(): String?

    fun setFireBaseToken(date: String)

    fun getMyTrackerInstanceId(): String?

    fun setMyTrackerInstanceId(date: String)

    fun getAppsFlyerInstanceId(): String?

    fun setAppsFlyerInstanceId(date: String)

    fun getCurrentDate(): String?

    fun setCurrentDate(date: String)

    fun getAppsFlyerConversion(): String?

    fun setSub2(date: String)

    fun getSub2(): String?
}