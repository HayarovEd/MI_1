package com.fast.zai.loans.data

import com.fast.zai.loans.domain.model.ElementOffer
import com.fast.zai.loans.domain.model.StatusApplication
import com.fast.zai.loans.domain.model.StatusApplication.Connect
import com.fast.zai.loans.domain.model.StatusApplication.Offer
import com.fast.zai.loans.domain.model.TypeCard.CardCredit
import com.fast.zai.loans.domain.model.TypeCard.CardDebit
import com.fast.zai.loans.domain.model.TypeCard.CardInstallment
import com.fast.zai.loans.domain.model.basedto.BaseState.Cards
import com.fast.zai.loans.domain.model.basedto.BaseState.Credits
import com.fast.zai.loans.domain.model.basedto.BaseState.Loans
import com.fast.zai.loans.domain.model.basedto.CardsCredit
import com.fast.zai.loans.domain.model.basedto.CardsDebit
import com.fast.zai.loans.domain.model.basedto.CardsInstallment
import com.fast.zai.loans.domain.model.basedto.Credit
import com.fast.zai.loans.domain.model.basedto.Loan


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
                if (position<=loans.size-1) {
                    val loan = loans[position]
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
                } else {
                    Connect(Loans)
                }
            }

            MESSAGE_CREDITS -> {
                if (position<=credits.size-1) {
                    val credit = credits[position]
                    Offer(
                        currentBaseState = Loans,
                        elementOffer = ElementOffer(
                            nameButton = credit.orderButtonText,
                            name = credit.name,
                            pathImage = credit.screen,
                            rang = credit.score,
                            description = credit.description,
                            amount = credit.summPrefix + " " + credit.summMin + " " + credit.summMid + " " + credit.summMax + " " + credit.summPostfix,
                            bet = credit.percentPrefix + " " + credit.percent + " " + credit.percentPostfix,
                            term = credit.termPrefix + " " + credit.termMin + " " + credit.termMid + " " + credit.termMax + " " + credit.termPostfix,
                            showMir = credit.showMir,
                            showVisa = credit.showVisa,
                            showMaster = credit.showMastercard,
                            showQiwi = credit.showQiwi,
                            showYandex = credit.showYandex,
                            showCache = credit.showCash,
                            showPercent = credit.hidePercentFields,
                            showTerm = credit.hideTermFields,
                            order = credit.order
                        )
                    )
                } else {
                    Connect(Credits)
                }
            }

            MESSAGE_CARDS_CREDIT -> {
                Connect(Cards(CardCredit))
                /*val loan = creditCards.firstOrNull { it.id==position }
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
                )*/
            }

            MESSAGE_CARDS_DEBIT -> {
                Connect(Cards(CardDebit))
                /*val loan = debitCards.firstOrNull { it.id==position }
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
                )*/
            }

            MESSAGE_CARDS_INSTALLMENT -> {
                Connect(Cards(CardInstallment))
                /*val loan = installmentCards.firstOrNull { it.id==position }
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
                )*/
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