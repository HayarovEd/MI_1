package lo.zaemtoperson.gola.domain.model.basedto

sealed class BaseState{
    object Loans:BaseState()
    object Cards:BaseState()
    object Credits:BaseState()
    object Offer:BaseState()
}
