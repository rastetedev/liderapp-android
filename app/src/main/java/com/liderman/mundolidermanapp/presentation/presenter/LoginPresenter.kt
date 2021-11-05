package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.domain.useCase.GetLogin
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class LoginPresenter(
    private var getLogin: GetLogin,
    private var methods: Methods
) : BasePresenter<LoginPresenter.View>() {

    fun makeLogin(dni: String, password: String) {
        if (!methods.isInternetConnected()) return

        view?.showLoading()

        getLogin.apply {
            this.dni = dni
            this.password = password
        }.run {
            execute(object : DisposableObserver<LoginEntity?>() {
                override fun onComplete() {
                    view?.hideLoading()
                }

                override fun onNext(t: LoginEntity) {
                    PapersManager.loginEntity = t
                    PapersManager.session = true
                    view?.successLogin()
                }

                override fun onError(e: Throwable) {
                    view?.hideLoading()

                    when (e) {
                        is HttpException -> {
                            when {
                                e.code() == 410 -> view?.showError(
                                    view?.getContext()!!
                                        .getString(R.string.error_login_credentials_dni)
                                )
                                e.code() == 411 -> view?.showError(
                                    view?.getContext()!!
                                        .getString(R.string.error_login_credentials_password)
                                )
                                e.code() == 412 -> view?.showError(
                                    view?.getContext()!!.getString(R.string.error_login_service)
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
    }

    interface View : BasePresenter.View {
        fun successLogin()
    }
}




