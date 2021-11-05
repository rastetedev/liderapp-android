package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.SgiDocRepository
import com.liderman.mundolidermanapp.domain.entity.sgidoc.SgiDocEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetSgiDoc(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: SgiDocRepository
) : UseCase<List<SgiDocEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<SgiDocEntity>?> {
        return repository.getSgiDoc()
    }
}