package lo.zaemtoperson.gola.domain.model

import lo.zaemtoperson.gola.domain.model.basedto.BaseState

sealed class StatusApplication {
    object Loading: StatusApplication()
    object Mock : StatusApplication()
    class Connect (val baseState: BaseState):StatusApplication()

    class Offer (
        val currentBaseState: BaseState,
        val name: String,
        val pathImage: String,
        val rang: String,
        val description: String,
        val amount: String,
        val bet:String,
        val term: String,
        val showMir: String,
        val showVisa: String,
        val showMaster: String,
        val showQiwi: String,
        val showYandex: String,
        val showCache: String,
        val nameButton:String
        ):StatusApplication()

    class Info (
        val currentBaseState: BaseState,
        val content: String
        ):StatusApplication()

    class Web (
        val url: String
    ):StatusApplication()

    object NoConnect:StatusApplication()
}
