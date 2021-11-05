package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.AuthRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetLogout(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AuthRepository
) : UseCase<Any>(executorThread, uiThread) {


    override fun createObservableUseCase(): Observable<Any> {
        return repository.logout()
    }
}