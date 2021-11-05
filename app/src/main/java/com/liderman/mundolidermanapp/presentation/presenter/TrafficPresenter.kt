package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.traffic.TrafficEntity
import com.liderman.mundolidermanapp.domain.useCase.GetTraffic
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class TrafficPresenter(
    private var getTraffic: GetTraffic,
    private var methods: Methods
) :
    BasePresenter<TrafficPresenter.View>() {

    fun getTraffic() {
        if (!methods.isInternetConnected()) return

        getTraffic.execute(object : DisposableObserver<TrafficEntity?>() {
            override fun onComplete() {
            }

            override fun onNext(t: TrafficEntity) {
                view?.successTrafficPresenter(t)
            }

            override fun onError(e: Throwable) {
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
        fun successTrafficPresenter(trafficEntity: TrafficEntity)
    }
}