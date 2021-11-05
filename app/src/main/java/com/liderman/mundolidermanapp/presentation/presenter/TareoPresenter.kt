package com.liderman.mundolidermanapp.presentation.presenter

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.task.TaskEntity
import com.liderman.mundolidermanapp.domain.useCase.GetTasks
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException

class TareoPresenter(
    private var getTasks: GetTasks,
    private var methods: Methods
) : BasePresenter<TareoPresenter.View>() {

    fun getTasks() {
        if (!methods.isInternetConnected()) return
        view?.showLoading()

        getTasks.execute(object : DisposableObserver<List<TaskEntity>?>() {
            override fun onNext(t: List<TaskEntity>) {
                view?.successTasks(t)
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
        fun successTasks(tasks: List<TaskEntity>)
    }
}