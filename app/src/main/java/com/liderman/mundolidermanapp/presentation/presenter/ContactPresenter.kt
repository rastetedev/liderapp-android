package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.useCase.QualityRequestContact
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.Serializable

class ContactPresenter(
    private var qualityRequestContact: QualityRequestContact,
    private var methods: Methods
) : BasePresenter<ContactPresenter.View>() {


    fun qualityServices(request: QualityRequest) {
        if (!methods.isInternetConnected()) return

        qualityRequestContact.qualityRequest = request
        qualityRequestContact.execute(object : DisposableObserver<Any>() {
            override fun onComplete() {
            }

            override fun onNext(t: Any) {
                view?.successContactPresenter(201)
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
        fun successContactPresenter(status: Int, vararg args: Serializable)
    }
}