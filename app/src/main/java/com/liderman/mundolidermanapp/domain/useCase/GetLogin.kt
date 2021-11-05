package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.AuthRepository
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetLogin(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AuthRepository
) : UseCase<LoginEntity?>(executorThread, uiThread) {

    var dni = ""
    var password = ""

    override fun createObservableUseCase(): Observable<LoginEntity?> {
        return repository.login(dni, password)
    }
}