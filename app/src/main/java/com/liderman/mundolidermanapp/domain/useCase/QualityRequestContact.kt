package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.ContactRepository
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class QualityRequestContact(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: ContactRepository
) : UseCase<Any>(executorThread, uiThread) {

    lateinit var qualityRequest: QualityRequest

    override fun createObservableUseCase(): Observable<Any> {
        return repository.qualityRequest(qualityRequest)
    }
}