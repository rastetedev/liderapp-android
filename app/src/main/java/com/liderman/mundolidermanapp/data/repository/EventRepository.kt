package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.request.EventRequest
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class EventRepository(private var apiService: ApiService) {

    fun sendEvent(
        evento: String
    ): Observable<Any> {

        return apiService.sendEvent(
            EventRequest(
                evento,
                PapersManager.loginEntity.zona,
                PapersManager.loginEntity.sexo,
                PapersManager.loginEntity.edad,
                PapersManager.loginEntity.fechaIngreso
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}