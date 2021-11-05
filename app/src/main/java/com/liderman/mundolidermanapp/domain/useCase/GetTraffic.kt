package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.TrafficRepository
import com.liderman.mundolidermanapp.domain.entity.traffic.TrafficEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetTraffic(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: TrafficRepository
) : UseCase<TrafficEntity?>(executorThread, uiThread) {

    override fun createObservableUseCase(): Observable<TrafficEntity?> {
        return repository.getTraffic()
    }
}