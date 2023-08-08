package lo.zaemtoperson.gola.presentation

import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.TypeCard
import lo.zaemtoperson.gola.domain.model.basedto.BaseState

sealed class MainEvent {
    object Reconnect:MainEvent()

    class OnChangeStatusApplication(val statusApplication: StatusApplication):MainEvent()
    class OnChangeBaseState(val baseState: BaseState):MainEvent()
    class OnChangeTypeCard(val typeCard: TypeCard):MainEvent()

    class OnGoToWeb(val urlOffer: String):MainEvent()
}
