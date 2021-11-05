package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.UtilidadesRepository
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetUtilidades(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: UtilidadesRepository
) : UseCase<UtilidadEntity?>(executorThread, uiThread) {

    var period = ""

    override fun createObservableUseCase(): Observable<UtilidadEntity?> {
        return repository.getUtilidades(period)
    }
}