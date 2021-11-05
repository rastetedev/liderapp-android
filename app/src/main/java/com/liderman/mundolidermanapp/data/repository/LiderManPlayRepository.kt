package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LiderManPlayRepository(private var apiService: ApiService) {

    fun getLidermanPlay(): Observable<List<LidermanPlayEntity>?> {

        return apiService.getLidermanPlay()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}