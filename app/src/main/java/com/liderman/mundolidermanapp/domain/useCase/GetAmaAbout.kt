package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import com.liderman.mundolidermanapp.data.repository.AmaRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetAmaAbout(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AmaRepository
) : UseCase<List<AmaTratoEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<AmaTratoEntity>?> {
        return repository.getAmaAbout()
    }
}