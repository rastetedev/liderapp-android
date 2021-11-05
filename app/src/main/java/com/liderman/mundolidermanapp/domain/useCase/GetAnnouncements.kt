package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.AnnouncementRepository
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetAnnouncements(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: AnnouncementRepository
) : UseCase<List<AnnouncementEntity>?>(executorThread, uiThread) {
    override fun createObservableUseCase(): Observable<List<AnnouncementEntity>?> {
        return repository.getAnnouncements()
    }
}