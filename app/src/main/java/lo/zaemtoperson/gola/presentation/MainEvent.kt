package lo.zaemtoperson.gola.presentation

import lo.zaemtoperson.gola.domain.model.basedto.BaseState

sealed class MainEvent {
    object Reconnect:MainEvent()
    class onChangeBaseState(val baseState: BaseState):MainEvent()
}
