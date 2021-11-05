package com.liderman.mundolidermanapp.di.module

import com.liderman.mundolidermanapp.data.repository.*
import com.liderman.mundolidermanapp.domain.useCase.*

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton

@Module
class UsesCaseModule {

    @Provides
    @Singleton
    fun getAnnouncements(
        repository: AnnouncementRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetAnnouncements(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getLidermanPlay(
        repository: LiderManPlayRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetLidermanPlay(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getLoans(
        repository: LoansRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetLoans(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getLogin(
        repository: AuthRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetLogin(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getLogout(
        repository: AuthRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetLogout(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getProfile(
        repository: ProfileRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetProfile(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getSgiDoc(
        repository: SgiDocRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetSgiDoc(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun updateProfile(
        repository: ProfileRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = UpdateProfile(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getLiderNet(
        repository: LiderNetRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetLiderNet(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getTasks(
        repository: TasksRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetTasks(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getTraffic(
        repository: TrafficRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetTraffic(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun responseLiderNet(
        repository: LiderNetRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = ResponseLiderNet(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getAmaPay(
        repository: AmaRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetAmaPay(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getAmaLife(
        repository: AmaRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetAmaLife(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getAmaAbout(
        repository: AmaRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetAmaAbout(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun uploadImage(
        repository: ProfileRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = UploadImage(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun qualityRequestContact(
        repository: ContactRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = QualityRequestContact(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getContactAreas(
        repository: ContactRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetContactAreas(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getPayments(
        repository: PaymentsRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetPayments(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getUtilidades(
        repository: UtilidadesRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetUtilidades(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun getContract(
        repository: ContractRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = GetContract(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun updateContract(
        repository: ContractRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = UpdateContract(executorThread, uiThread, repository)

    @Provides
    @Singleton
    fun sendEvent(
        repository: EventRepository,
        @Named("executor_thread") executorThread: Scheduler,
        @Named("ui_thread") uiThread: Scheduler
    ) = SendEvent(executorThread, uiThread, repository)


    /** always in bottom */
    @Provides
    @Named("executor_thread")
    fun provideExecutorThread(): Scheduler = Schedulers.io()

    @Provides
    @Named("ui_thread")
    fun provideUiThread(): Scheduler = AndroidSchedulers.mainThread()

}