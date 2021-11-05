package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.ContractRepository
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class UpdateContract(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: ContractRepository
) : UseCase<ContractEntity?>(executorThread, uiThread) {

    var id : Int = 0
    var state: Int = 0

    override fun createObservableUseCase(): Observable<ContractEntity?> {
        return repository.updateContractState(id, state )
    }
}