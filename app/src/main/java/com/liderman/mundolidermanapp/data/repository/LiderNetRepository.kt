package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.data.request.LiderNetRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LiderNetRepository(private var apiService: ApiService) {

    fun getLiderNet(): Observable<LiderNetEntity?> {

        return apiService.getLiderNet(PapersManager.loginEntity.userId)
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun responseLiderNet(request: LiderNetRequest): Observable<Any> {

        return apiService.responseLiderNet(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}