package lo.zaemtoperson.gola.domain

interface SharedKepper {

    fun getFireBaseToken(): String?

    fun setFireBaseToken(date: String)

    fun getMyTrackerInstanceId(): String?

    fun setMyTrackerInstanceId(date: String)
}