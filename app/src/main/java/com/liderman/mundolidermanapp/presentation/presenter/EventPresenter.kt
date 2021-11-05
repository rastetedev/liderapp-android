package com.liderman.mundolidermanapp.presentation.presenter

import android.util.Log
import com.liderman.mundolidermanapp.domain.useCase.SendEvent
import com.liderman.mundolidermanapp.presentation.presenter.base.BasePresenter
import com.liderman.mundolidermanapp.utils.Methods
import io.reactivex.observers.DisposableObserver

class EventPresenter(
    private var sendEvent: SendEvent,
    private var methods: Methods
) : BasePresenter<EventPresenter.View>() {

    fun sendEvent(evento: String) {
        if (!methods.isInternetConnected()) return
        sendEvent.evento = evento
        sendEvent.execute(object : DisposableObserver<Any>() {
            override fun onComplete() {
                //
            }

            override fun onError(e: Throwable) {
                //
                Log.e("ERROR EVENT", e.message.toString())
            }

            override fun onNext(t: Any) {
                Log.e("SUCCESS EVENT", evento)
            }

        })
    }

    interface View : BasePresenter.View {

    }
}