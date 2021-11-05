package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoansRepository(private var apiService: ApiService) {

    fun getLoans(): Observable<List<BorrowEntity>?> {

        return apiService.getBorrows(
            PapersManager.loginEntity.userId,
            PapersManager.loginEntity.token
        )
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}