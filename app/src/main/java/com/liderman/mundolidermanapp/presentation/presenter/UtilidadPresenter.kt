package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import com.liderman.mundolidermanapp.domain.useCase.GetUtilidades
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class UtilidadPresenter(
    private var getUtilidades: GetUtilidades,
    private var methods: Methods
) : BasePresenter<UtilidadPresenter.View>() {

    fun getUtilidades(period: String) {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getUtilidades.period = period

        getUtilidades.execute(object : DisposableObserver<UtilidadEntity?>() {
            override fun onNext(t: UtilidadEntity) {
                view?.successUtilidad(t)
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
        fun successUtilidad(utilidadEntity: UtilidadEntity)
    }
}