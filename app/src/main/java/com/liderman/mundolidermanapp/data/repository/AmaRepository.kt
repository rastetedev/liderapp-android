package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AmaRepository(private var apiService: ApiService) {

    fun getAmaAbout(): Observable<List<AmaTratoEntity>?> {
        return apiService.getAmaTrata()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAmaPay(): Observable<List<AmaPayEntity>?> {
        return apiService.getAmaPay()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun getAmaLife(): Observable<List<AmaLifeEntity>?> {
        return apiService.getAmaLife()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}