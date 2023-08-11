package lo.zaemtoperson.gola.data

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import lo.zaemtoperson.gola.domain.model.ElementOffer
import lo.zaemtoperson.gola.domain.model.StatusApplication
import lo.zaemtoperson.gola.domain.model.StatusApplication.Connect
import lo.zaemtoperson.gola.domain.model.StatusApplication.Offer
import lo.zaemtoperson.gola.domain.model.TypeCard.CardCredit
import lo.zaemtoperson.gola.domain.model.TypeCard.CardDebit
import lo.zaemtoperson.gola.domain.model.TypeCard.CardInstallment
import lo.zaemtoperson.gola.domain.model.basedto.BaseState.Cards
import lo.zaemtoperson.gola.domain.model.basedto.BaseState.Credits
import lo.zaemtoperson.gola.domain.model.basedto.BaseState.Loans
import lo.zaemtoperson.gola.domain.model.basedto.CardsCredit
import lo.zaemtoperson.gola.domain.model.basedto.CardsDebit
import lo.zaemtoperson.gola.domain.model.basedto.CardsInstallment
import lo.zaemtoperson.gola.domain.model.basedto.Credit
import lo.zaemtoperson.gola.domain.model.basedto.Loan


val permissions = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val permissions34 = arrayOf(
    Manifest.permission.CAMERA,
)

fun String.setStatusByPush(
    loans:List<Loan>,
    credits:List<Credit>,
    creditCards:List<CardsCredit>,
    debitCards:List<CardsDebit>,
    installmentCards:List<CardsInstallment>
): StatusApplication {
    val subString = this.split("/")
    if (subString.size == 1) {
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                Connect(Loans)
            }

            MESSAGE_CREDITS -> {
                Connect(Credits)
            }

            MESSAGE_CARDS_CREDIT -> {
                Connect(Cards(CardCredit))
            }

            MESSAGE_CARDS_DEBIT -> {
                Connect(Cards(CardDebit))
            }

            MESSAGE_CARDS_INSTALLMENT -> {
                Connect(Cards(CardInstallment))
            }

            else -> {
                Connect(Loans)
            }
        }
    } else {
        val position = subString.last().toInt()
        return when (subString.first()) {
            MESSAGE_LOANS -> {
                val loan = loans.find { it.id==position }
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan?.orderButtonText?:"",
                        name = loan?.name?:"",
                        pathImage = loan?.screen?:"",
                        rang = loan?.score?:"",
                        description = loan?.description?:"",
                        amount = loan?.summPrefix + " " + loan?.summMin + " " + loan?.summMid + " " + loan?.summMax + " " + loan?.summPostfix,
                        bet = loan?.percentPrefix + " " + loan?.percent + " " + loan?.percentPostfix,
                        term = loan?.termPrefix + " " + loan?.termMin + " " + loan?.termMid + " " + loan?.termMax + " " + loan?.termPostfix,
                        showMir = loan?.showMir?:"",
                        showVisa = loan?.showVisa?:"",
                        showMaster = loan?.showMastercard?:"",
                        showQiwi = loan?.showQiwi?:"",
                        showYandex = loan?.showYandex?:"",
                        showCache = loan?.showCash?:"",
                        showPercent = loan?.hidePercentFields?:"",
                        showTerm = loan?.hideTermFields?:"",
                        order = loan?.order?:""
                    )
                )
            }

            MESSAGE_CREDITS -> {
                val loan = credits.find { it.id==position }
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan?.orderButtonText?:"",
                        name = loan?.name?:"",
                        pathImage = loan?.screen?:"",
                        rang = loan?.score?:"",
                        description = loan?.description?:"",
                        amount = loan?.summPrefix + " " + loan?.summMin + " " + loan?.summMid + " " + loan?.summMax + " " + loan?.summPostfix,
                        bet = loan?.percentPrefix + " " + loan?.percent + " " + loan?.percentPostfix,
                        term = loan?.termPrefix + " " + loan?.termMin + " " + loan?.termMid + " " + loan?.termMax + " " + loan?.termPostfix,
                        showMir = loan?.showMir?:"",
                        showVisa = loan?.showVisa?:"",
                        showMaster = loan?.showMastercard?:"",
                        showQiwi = loan?.showQiwi?:"",
                        showYandex = loan?.showYandex?:"",
                        showCache = loan?.showCash?:"",
                        showPercent = loan?.hidePercentFields?:"",
                        showTerm = loan?.hideTermFields?:"",
                        order = loan?.order?:""
                    )
                )
            }

            MESSAGE_CARDS_CREDIT -> {
                val loan = creditCards.find { it.id==position }
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan?.orderButtonText?:"",
                        name = loan?.name?:"",
                        pathImage = loan?.screen?:"",
                        rang = loan?.score?:"",
                        description = loan?.description?:"",
                        amount = loan?.summPrefix + " " + loan?.summMin + " " + loan?.summMid + " " + loan?.summMax + " " + loan?.summPostfix,
                        bet = loan?.percentPrefix + " " + loan?.percent + " " + loan?.percentPostfix,
                        term = loan?.termPrefix + " " + loan?.termMin + " " + loan?.termMid + " " + loan?.termMax + " " + loan?.termPostfix,
                        showMir = loan?.showMir?:"",
                        showVisa = loan?.showVisa?:"",
                        showMaster = loan?.showMastercard?:"",
                        showQiwi = loan?.showQiwi?:"",
                        showYandex = loan?.showYandex?:"",
                        showCache = loan?.showCash?:"",
                        showPercent = loan?.hidePercentFields?:"",
                        showTerm = loan?.hideTermFields?:"",
                        order = loan?.order?:""
                    )
                )
            }

            MESSAGE_CARDS_DEBIT -> {
                val loan = debitCards.find { it.id==position }
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan?.orderButtonText?:"",
                        name = loan?.name?:"",
                        pathImage = loan?.screen?:"",
                        rang = loan?.score?:"",
                        description = loan?.description?:"",
                        amount = loan?.summPrefix + " " + loan?.summMin + " " + loan?.summMid + " " + loan?.summMax + " " + loan?.summPostfix,
                        bet = loan?.percentPrefix + " " + loan?.percent + " " + loan?.percentPostfix,
                        term = loan?.termPrefix + " " + loan?.termMin + " " + loan?.termMid + " " + loan?.termMax + " " + loan?.termPostfix,
                        showMir = loan?.showMir?:"",
                        showVisa = loan?.showVisa?:"",
                        showMaster = loan?.showMastercard?:"",
                        showQiwi = loan?.showQiwi?:"",
                        showYandex = loan?.showYandex?:"",
                        showCache = loan?.showCash?:"",
                        showPercent = loan?.hidePercentFields?:"",
                        showTerm = loan?.hideTermFields?:"",
                        order = loan?.order?:""
                    )
                )
            }

            MESSAGE_CARDS_INSTALLMENT -> {
                val loan = installmentCards.find { it.id==position }
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan?.orderButtonText?:"",
                        name = loan?.name?:"",
                        pathImage = loan?.screen?:"",
                        rang = loan?.score?:"",
                        description = loan?.description?:"",
                        amount = loan?.summPrefix + " " + loan?.summMin + " " + loan?.summMid + " " + loan?.summMax + " " + loan?.summPostfix,
                        bet = loan?.percentPrefix + " " + loan?.percent + " " + loan?.percentPostfix,
                        term = loan?.termPrefix + " " + loan?.termMin + " " + loan?.termMid + " " + loan?.termMax + " " + loan?.termPostfix,
                        showMir = loan?.showMir?:"",
                        showVisa = loan?.showVisa?:"",
                        showMaster = loan?.showMastercard?:"",
                        showQiwi = loan?.showQiwi?:"",
                        showYandex = loan?.showYandex?:"",
                        showCache = loan?.showCash?:"",
                        showPercent = loan?.hidePercentFields?:"",
                        showTerm = loan?.hideTermFields?:"",
                        order = loan?.order?:""
                    )
                )
            }

            else -> {
                val loan = loans[0]
                Offer(
                    currentBaseState = Loans,
                    elementOffer = ElementOffer(
                        nameButton = loan.orderButtonText,
                        name = loan.name,
                        pathImage = loan.screen,
                        rang = loan.score,
                        description = loan.description,
                        amount = loan.summPrefix + " " + loan.summMin + " " + loan.summMid + " " + loan.summMax + " " + loan.summPostfix,
                        bet = loan.percentPrefix + " " + loan.percent + " " + loan.percentPostfix,
                        term = loan.termPrefix + " " + loan.termMin + " " + loan.termMid + " " + loan.termMax + " " + loan.termPostfix,
                        showMir = loan.showMir,
                        showVisa = loan.showVisa,
                        showMaster = loan.showMastercard,
                        showQiwi = loan.showQiwi,
                        showYandex = loan.showYandex,
                        showCache = loan.showCash,
                        showPercent = loan.hidePercentFields,
                        showTerm = loan.hideTermFields,
                        order = loan.order
                    )
                )
            }
        }

    }
}

