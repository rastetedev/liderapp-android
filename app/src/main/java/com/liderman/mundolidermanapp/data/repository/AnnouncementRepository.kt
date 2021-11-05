package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AnnouncementRepository(private var apiService: ApiService) {

    fun getAnnouncements(): Observable<List<AnnouncementEntity>?> {
        return apiService.getAnnouncements()
            .flatMap {
                Observable.just(it.result)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}