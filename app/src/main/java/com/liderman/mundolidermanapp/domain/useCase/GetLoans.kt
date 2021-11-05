package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.LoansRepository
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetLoans(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: LoansRepository
) : UseCase<List<BorrowEntity>?>(executorThread, uiThread) {

    override fun createObservableUseCase(): Observable<List<BorrowEntity>?> {
        return repository.getLoans()
    }
}