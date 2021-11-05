package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.repository.ContactRepository
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetContactAreas(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: ContactRepository
) : UseCase<List<ContactEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<ContactEntity>?> {
        return repository.contactAreas()
    }
}