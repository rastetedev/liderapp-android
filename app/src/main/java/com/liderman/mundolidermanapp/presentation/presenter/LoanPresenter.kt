package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import com.liderman.mundolidermanapp.domain.useCase.GetLoans
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class LoanPresenter(private var getLoans: GetLoans, private var methods: Methods) :
    BasePresenter<LoanPresenter.View>() {

    fun getLoans() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getLoans.execute(object : DisposableObserver<List<BorrowEntity>?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: List<BorrowEntity>) {
                view?.successLoanPresenter(t)
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

        })
    }

    interface View : BasePresenter.View {
        fun successLoanPresenter(loans: List<BorrowEntity>)
    }
}