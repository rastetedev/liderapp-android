package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R

import com.liderman.mundolidermanapp.data.request.UpdateProfileRequest
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.domain.useCase.*
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.Serializable

class ProfilePresenter(
    private var getProfile: GetProfile,
    private var updateProfile: UpdateProfile,
    private var uploadImage: UploadImage,
    private var getLogout: GetLogout,
    private var methods: Methods
) : BasePresenter<ProfilePresenter.View>() {

    fun getProfile() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getProfile.execute(object : DisposableObserver<LoginEntity?>() {
            override fun onNext(t: LoginEntity) {
                PapersManager.loginEntity = t
                view?.successProfilePresenter(202, t)
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

    fun updateProfile(updateProfileRequest: UpdateProfileRequest) {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        updateProfile.updateData = updateProfileRequest
        updateProfile.execute(object : DisposableObserver<LoginEntity?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: LoginEntity) {
                PapersManager.loginEntity = t
                view?.successProfilePresenter(202, t)
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

    fun uploadImage(filePath: String) {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        uploadImage.filePath = filePath
        uploadImage.execute(object : DisposableObserver<LoginEntity?>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: LoginEntity) {
                PapersManager.loginEntity = t
                view?.successProfilePresenter(208, t)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                when (e) {
                    is HttpException -> {
                        when {
                            e.code() == 410 -> view?.showError(
                                view?.getContext()!!
                                    .getString(R.string.error_profile_image_no_found)
                            )
                            e.code() == 411 -> view?.showError(
                                view?.getContext()!!
                                    .getString(R.string.error_profile_image_no_compatible)
                            )
                            e.code() == 412 -> view?.showError(
                                view?.getContext()!!
                                    .getString(R.string.try_error)
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

    fun logout() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getLogout.execute(object : DisposableObserver<Any>() {
            override fun onComplete() {
                view?.hideLoading()
            }

            override fun onNext(t: Any) {
                PapersManager.session = false
                PapersManager.loginEntity = LoginEntity.default()
                view?.successLogout(true)
            }

            override fun onError(e: Throwable) {
                view?.hideLoading()
                PapersManager.session = false
                PapersManager.loginEntity = LoginEntity.default()
                view?.successLogout(false)
            }

        })
    }

    interface View : BasePresenter.View {
        fun successProfilePresenter(status: Int, loginEntity: LoginEntity)
        fun successLogout(success: Boolean)
    }
}