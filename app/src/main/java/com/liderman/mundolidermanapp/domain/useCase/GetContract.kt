package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.ContractRepository
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler


class GetContract (
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: ContractRepository
) : UseCase<ContractEntity?>(executorThread, uiThread) {

    override fun createObservableUseCase(): Observable<ContractEntity?> {
        return repository.getContract()
    }
}