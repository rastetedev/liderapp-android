package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.PaymentsRepository
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetPayments(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: PaymentsRepository
) : UseCase<PaymentEntity?>(executorThread, uiThread) {

    var period = ""

    override fun createObservableUseCase(): Observable<PaymentEntity?> {
        return repository.getPayments(period)
    }
}