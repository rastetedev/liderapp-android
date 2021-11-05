package com.liderman.mundolidermanapp.di.module

import com.liderman.mundolidermanapp.data.repository.*
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun loginRepository(apiService: ApiService) = AuthRepository(apiService)

    @Provides
    @Singleton
    fun profileRepository(apiService: ApiService) = ProfileRepository(apiService)

    @Provides
    @Singleton
    fun announcementRepository(apiService: ApiService) = AnnouncementRepository(apiService)

    @Provides
    @Singleton
    fun liderManPlayRepository(apiService: ApiService) = LiderManPlayRepository(apiService)

    @Provides
    @Singleton
    fun sgiDocRepository(apiService: ApiService) = SgiDocRepository(apiService)

    @Provides
    @Singleton
    fun liderNetRepository(apiService: ApiService) = LiderNetRepository(apiService)

    @Provides
    @Singleton
    fun taskRespository(apiService: ApiService) = TasksRepository(apiService)

    @Provides
    @Singleton
    fun trafficRepository(apiService: ApiService) = TrafficRepository(apiService)

    @Provides
    @Singleton
    fun amaRepository(apiService: ApiService) = AmaRepository(apiService)

    @Provides
    @Singleton
    fun contactRepository(apiService: ApiService) = ContactRepository(apiService)

    @Provides
    @Singleton
    fun loansRepository(apiService: ApiService) = LoansRepository(apiService)

    @Provides
    @Singleton
    fun paymentsRepository(apiService: ApiService) = PaymentsRepository(apiService)

    @Provides
    @Singleton
    fun utilidadesRepository(apiService: ApiService) = UtilidadesRepository(apiService)

    @Provides
    @Singleton
    fun contractsRepository(apiService: ApiService) = ContractRepository(apiService)

    @Provides
    @Singleton
    fun eventRepository(apiService: ApiService) = EventRepository(apiService)
}