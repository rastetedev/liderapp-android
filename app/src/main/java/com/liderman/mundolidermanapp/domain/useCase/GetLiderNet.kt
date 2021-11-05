package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.data.repository.LiderNetRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetLiderNet(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: LiderNetRepository
) : UseCase<LiderNetEntity?>(executorThread, uiThread) {

    override fun createObservableUseCase(): Observable<LiderNetEntity?> {
        return repository.getLiderNet()
    }
}