package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.LiderNetRepository
import com.liderman.mundolidermanapp.data.request.LiderNetRequest
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class ResponseLiderNet(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: LiderNetRepository
) : UseCase<Any>(executorThread, uiThread) {

    lateinit var lidernetRequest: LiderNetRequest
    override fun createObservableUseCase(): Observable<Any> {
        return repository.responseLiderNet(lidernetRequest)
    }
}