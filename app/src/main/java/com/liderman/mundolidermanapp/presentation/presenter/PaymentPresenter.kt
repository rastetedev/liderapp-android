package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentEntity
import com.liderman.mundolidermanapp.domain.useCase.GetPayments
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class PaymentPresenter(
    private var getPayments: GetPayments,
    private var methods: Methods
) : BasePresenter<PaymentPresenter.View>() {

    fun getPayments(period: String) {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getPayments.period = period

        getPayments.execute(object : DisposableObserver<PaymentEntity?>() {
            override fun onNext(t: PaymentEntity) {
                view?.successPayment(t)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 410 -> view?.showError(
                                view?.getContext()!!.getString(R.string.try_error)
                            )
                            e.code() == 500 -> view?.showError(
                                view?.getContext()!!.getString(R.string.service_error)
                            )
                        }
                    }
                    else -> view?.showError(
                        view?.getContext()!!.getString(R.string.unknown_error)
                    )
                }
            }

            override fun onComplete() {
                view?.hideLoading()
            }

        })
    }


    interface View : BasePresenter.View {
        fun successPayment(paymentEntity: PaymentEntity)
    }
}