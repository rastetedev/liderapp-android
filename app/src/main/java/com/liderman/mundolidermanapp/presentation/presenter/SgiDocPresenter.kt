package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.sgidoc.SgiDocEntity
import com.liderman.mundolidermanapp.domain.useCase.GetSgiDoc
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import com.liderman.mundolidermanapp.utils.Methods
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class SgiDocPresenter(
    private var getSgiDoc: GetSgiDoc,
    private var methods: Methods
) : BasePresenter<SgiDocPresenter.View>() {

    fun getSgiDoc() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()
        getSgiDoc.execute(object : DisposableObserver<List<SgiDocEntity>?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: List<SgiDocEntity>) {
                view?.successSgiDocs(t)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
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
        fun successSgiDocs(sgiDocs: List<SgiDocEntity>)

    }
}