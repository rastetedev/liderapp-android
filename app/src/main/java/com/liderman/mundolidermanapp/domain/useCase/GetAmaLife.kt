package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import com.liderman.mundolidermanapp.data.repository.AmaRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetAmaLife(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AmaRepository
) : UseCase<List<AmaLifeEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<AmaLifeEntity>?> {
        return repository.getAmaLife()
    }
}