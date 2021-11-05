package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PaymentsRepository(private var apiService: ApiService) {

    fun getPayments(period: String): Observable<PaymentEntity?> {

        return apiService.getPayments(
            PapersManager.loginEntity.userId,
            period,
            PapersManager.loginEntity.token
        )
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

