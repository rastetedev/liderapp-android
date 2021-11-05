package com.liderman.mundolidermanapp.domain.useCase

import com.liderman.mundolidermanapp.data.repository.TasksRepository
import com.liderman.mundolidermanapp.domain.entity.task.TaskEntity
import com.liderman.mundolidermanapp.domain.useCase.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetTasks(
    executorThread: Scheduler,
    uiThread: Scheduler,
    private var repository: TasksRepository
) : UseCase<List<TaskEntity>?>(executorThread, uiThread) {

    override fun createObservableUseCase(): Observable<List<TaskEntity>?> {
        return repository.getTasks()
    }
}