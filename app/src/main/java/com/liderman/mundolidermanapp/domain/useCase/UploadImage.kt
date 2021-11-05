package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.ProfileRepository
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class UploadImage(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: ProfileRepository
) : UseCase<LoginEntity?>(executorThread, uiThread) {

    var filePath = ""

    override fun createObservableUseCase(): Observable<LoginEntity?> {
        return repository.uploadImage(filePath )
    }
}