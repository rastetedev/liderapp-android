package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.EventRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class SendEvent(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: EventRepository
) : UseCase<Any>(executorThread, uiThread) {

    lateinit var evento : String

    override fun createObservableUseCase(): Observable<Any> {
        return repository.sendEvent(evento)
    }
}