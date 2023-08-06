package lo.zaemtoperson.gola.domain.model

sealed class StatusApplication {
    class Mock():StatusApplication()
    class Connect:StatusApplication()
}
