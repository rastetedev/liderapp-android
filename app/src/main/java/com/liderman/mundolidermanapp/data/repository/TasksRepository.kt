package com.liderman.mundolidermanapp.data.repository

import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.domain.entity.task.TaskEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TasksRepository(private var apiService: ApiService) {

    fun getTasks(): Observable<List<TaskEntity>?> {
        return apiService.getTasks(
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