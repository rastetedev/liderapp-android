package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.data.request.LiderNetRequest
import com.liderman.mundolidermanapp.domain.useCase.GetLiderNet
import com.liderman.mundolidermanapp.domain.useCase.ResponseLiderNet
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class LiderNetPresenter(
    private var getLiderNet: GetLiderNet,
    private var responseLiderNet: ResponseLiderNet,
    private var methods: Methods
) : BasePresenter<LiderNetPresenter.View>() {

    fun getLiderNet() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getLiderNet.execute(object : DisposableObserver<LiderNetEntity?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: LiderNetEntity) {
                view?.successLiderNet(t)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 410 -> view?.showError(
                                view?.getContext()!!.getString(R.string.error_lidernet_load)
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

    fun responseLiderNet(request: LiderNetRequest) {
        if (!methods.isInternetConnected()) return
        view?.showLoading()
        responseLiderNet.lidernetRequest = request
        responseLiderNet.execute(object : DisposableObserver<Any>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: Any) {
                //
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 410 -> {
                                view?.showError(
                                    view?.getContext()!!.getString(R.string.error_lidernet_finished)
                                )
                            }
                            e.code() == 411 -> {
                                view?.showError(
                                    view?.getContext()!!.getString(R.string.error_lidernet_answer)
                                )
                            }
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
        fun successLiderNet(lidernet: LiderNetEntity)
    }
}