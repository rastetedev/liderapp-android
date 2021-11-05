package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import com.liderman.mundolidermanapp.domain.useCase.GetLidermanPlay
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import com.liderman.mundolidermanapp.utils.Methods
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class PlayPresenter(
    private var getLidermanPlay: GetLidermanPlay,
    private var methods: Methods
) : BasePresenter<PlayPresenter.View>() {

    fun getLidermanPlay() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()
        getLidermanPlay.execute(object : DisposableObserver<List<LidermanPlayEntity>?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: List<LidermanPlayEntity>) {
                view?.successLidermaPlay(t)
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
        fun successLidermaPlay(lidermanPlays: List<LidermanPlayEntity>)
    }
}