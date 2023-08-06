package lo.zaemtoperson.gola.domain.model

import lo.zaemtoperson.gola.domain.model.basedto.BaseState

sealed class StatusApplication {
    object Mock : StatusApplication()
    class Connect (val baseState: BaseState):StatusApplication()
}
