package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import com.liderman.mundolidermanapp.data.repository.LiderManPlayRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetLidermanPlay(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: LiderManPlayRepository
) : UseCase<List<LidermanPlayEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<LidermanPlayEntity>?> {
        return repository.getLidermanPlay()
    }
}