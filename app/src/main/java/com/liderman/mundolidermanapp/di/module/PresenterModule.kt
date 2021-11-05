package com.liderman.mundolidermanapp.di.module

import com.liderman.mundolidermanapp.di.PresenterScope
import com.liderman.mundolidermanapp.domain.useCase.*
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.presenter.*
import com.liderman.mundolidermanapp.presentation.presenter.LoginPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    @PresenterScope
    fun loginPresenter(
        useCase: GetLogin,
        methods: Methods
    ) = LoginPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun masterPresenter(
        useCase2: GetAnnouncements,
        usecase4: GetContactAreas,
        methods: Methods
    ) = MasterPresenter(useCase2, usecase4, methods)

    @Provides
    @PresenterScope
    fun playPresenter(
        useCase: GetLidermanPlay,
        methods: Methods
    ) = PlayPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun sgiDocPresenter(
        useCase: GetSgiDoc,
        methods: Methods
    ) = SgiDocPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun profilePresenter(
        useCase: GetProfile,
        useCase2: UpdateProfile,
        useCase3: UploadImage,
        useCase4: GetLogout,
        methods: Methods
    ) = ProfilePresenter(useCase, useCase2, useCase3, useCase4, methods)

    @Provides
    @PresenterScope
    fun liderNetPresenter(
        useCase: GetLiderNet,
        useCase2: ResponseLiderNet,
        methods: Methods
    ) = LiderNetPresenter(useCase, useCase2, methods)

    @Provides
    @PresenterScope
    fun taskPresenter(
        useCase: GetTasks,
        methods: Methods
    ) = TareoPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun trafficPresenter(
        useCase: GetTraffic,
        methods: Methods
    ) = TrafficPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun amaPresenter(
        useCase: GetAmaPay,
        useCase2: GetAmaLife,
        useCase3: GetAmaAbout,
        methods: Methods
    ) = AmaPresenter(useCase, useCase2, useCase3, methods)

    @Provides
    @PresenterScope
    fun contactPresenter(
        useCase: QualityRequestContact,
        methods: Methods
    ) = ContactPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun contractPresenter(
        useCase: GetContract,
        useCase2: UpdateContract,
        methods: Methods
    ) = ContractPresenter(useCase, useCase2, methods)

    @Provides
    @PresenterScope
    fun loanPresenter(
        useCase: GetLoans,
        methods: Methods
    ) = LoanPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun paymentsPresenter(
        useCase: GetPayments,
        methods: Methods
    ) = PaymentPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun utilidadesPresenter(
        useCase: GetUtilidades,
        methods: Methods
    ) = UtilidadPresenter(useCase, methods)

    @Provides
    @PresenterScope
    fun eventPresenter(
        useCase: SendEvent,
        methods: Methods
    ) = EventPresenter(useCase, methods)

}
