package lo.zaemtoperson.gola.domain.model

sealed interface TypeCard{
    object CardCredit:TypeCard
    object CardDebit:TypeCard
    object CardInstallment:TypeCard
}