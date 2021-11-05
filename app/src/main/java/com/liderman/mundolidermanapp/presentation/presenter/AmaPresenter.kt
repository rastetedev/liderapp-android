package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import com.liderman.mundolidermanapp.domain.useCase.GetAmaAbout
import com.liderman.mundolidermanapp.domain.useCase.GetAmaLife
import com.liderman.mundolidermanapp.domain.useCase.GetAmaPay
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class AmaPresenter(
    private var getAmaPay: GetAmaPay,
    private var getAmaLife: GetAmaLife,
    private var getAmaAbout: GetAmaAbout,
    private var methods: Methods
) : BasePresenter<AmaPresenter.View>() {

    fun getAmaPay() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getAmaPay.execute(object : DisposableObserver<List<AmaPayEntity>?>() {
            override fun onNext(t: List<AmaPayEntity>) {
                view?.successAmaPay(t)
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

            override fun onComplete() {
                view?.hideLoading()
            }

        })

    }

    fun getAmaLife() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getAmaLife.execute(object : DisposableObserver<List<AmaLifeEntity>?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: List<AmaLifeEntity>) {
                view?.successAmaLife(t)
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

    fun getAmaAbout() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getAmaAbout.execute(object : DisposableObserver<List<AmaTratoEntity>?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: List<AmaTratoEntity>) {
                view?.successAmaTrato(t)
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
        fun successAmaLife(amaLifes: List<AmaLifeEntity>)

        fun successAmaTrato(amaTratos: List<AmaTratoEntity>)

        fun successAmaPay(amaPays: List<AmaPayEntity>)
    }
}