package lo.zaemtoperson.gola.domain.model.basedto

import lo.zaemtoperson.gola.domain.model.TypeCard

sealed class BaseState{
    object Loans:BaseState()
    class Cards(val typeCard: TypeCard):BaseState()
    object Credits:BaseState()
}
