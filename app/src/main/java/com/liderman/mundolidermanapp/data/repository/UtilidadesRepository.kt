package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UtilidadesRepository(private var apiService: ApiService) {

    fun getUtilidades(period: String): Observable<UtilidadEntity?> {

        return apiService.getUtilidades(
            PapersManager.loginEntity.userId,
            period,
            PapersManager.loginEntity.token
        )
            .flatMap {
                Observable.just(it.result?.get(0))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

