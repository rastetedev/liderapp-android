package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import com.liderman.mundolidermanapp.data.repository.AmaRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetAmaPay(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AmaRepository
) : UseCase<List<AmaPayEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<AmaPayEntity>?> {
        return repository.getAmaPay()
    }
}