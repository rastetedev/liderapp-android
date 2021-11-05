package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactRepository(private var apiService: ApiService) {

    fun contactAreas(): Observable<List<ContactEntity>?> {
        return apiService.contactAreas()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun qualityRequest(request: QualityRequest): Observable<Any> {
        return apiService.qualityRequest(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}