package com.liderman.mundolidermanapp.presentation.presenter.base

import android.content.Context
import androidx.annotation.StringRes
import io.reactivex.disposables.Disposable

open class BasePresenter<V : BasePresenter.View> {

    protected var view: V? = null
    private var disposable: Disposable? = null

    fun attachView(view: V) {
        this.view = view
    }

    open fun detachView() {
        view = null
        disposable?.dispose()
        disposable = null
    }

    interface View {
        fun getContext(): Context

        fun showLoading()

        fun hideLoading()

        fun showError(message: String)

        fun showError(@StringRes message: Int)

        fun showSessionFinish()
    }
}