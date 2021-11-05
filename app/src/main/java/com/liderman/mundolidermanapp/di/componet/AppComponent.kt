package com.liderman.mundolidermanapp.di.componet

import android.content.Context
import com.liderman.mundolidermanapp.data.retrofit.ApiService
import com.liderman.mundolidermanapp.di.module.*
import com.liderman.mundolidermanapp.domain.useCase.*
import com.liderman.mundolidermanapp.utils.Methods
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, MethodsModule::class, UsesCaseModule::class, RepositoryModule::class]) //, ThreadModule::class
interface AppComponent {

    fun context(): Context

    fun apiService(): ApiService

    fun methods(): Methods

    /**use case */
    fun getAnnouncements(): GetAnnouncements
    fun getLidermanPlay(): GetLidermanPlay
    fun getLoginUseCase(): GetLogin
    fun getLogoutUseCase(): GetLogout
    fun getProfileUseCase(): GetProfile
    fun getSgiDoc(): GetSgiDoc
    fun updateProfile(): UpdateProfile
    fun getLiderNet(): GetLiderNet
    fun getTraffic(): GetTraffic
    fun getPayments(): GetPayments
    fun getUtilidades(): GetUtilidades
    fun getLoans(): GetLoans
    fun responseLiderNet(): ResponseLiderNet
    fun getAmaPay(): GetAmaPay
    fun getTasks(): GetTasks
    fun getAmaLife(): GetAmaLife
    fun getAmaAbout(): GetAmaAbout
    fun uploadImage(): UploadImage
    fun getContactAreas(): GetContactAreas
    fun qualityRequestContact(): QualityRequestContact
    fun getContract(): GetContract
    fun updateContract(): UpdateContract
    fun sendEvent(): SendEvent

}
