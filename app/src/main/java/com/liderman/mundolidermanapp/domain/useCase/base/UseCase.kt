package com.liderman.mundolidermanapp.domain.useCase.base

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

abstract class UseCase<T>(
    private var executorThread: Scheduler,
    private var uiThread: Scheduler,
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()
) {

    fun execute(disposableObserver: DisposableObserver<T>) {

        val observable = this.createObservableUseCase().subscribeOn(executorThread).observeOn(uiThread)

        val observer = observable.subscribeWith(disposableObserver)
        compositeDisposable.add(observer)
    }

    protected abstract fun createObservableUseCase(): Observable<T>

    fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}