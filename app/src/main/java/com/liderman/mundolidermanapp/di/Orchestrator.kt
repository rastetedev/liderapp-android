package com.liderman.mundolidermanapp.di

import com.liderman.mundolidermanapp.di.componet.DaggerPresenterComponent
import com.liderman.mundolidermanapp.di.componet.PresenterComponent
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication


object Orchestrator {

    val presenterComponent: PresenterComponent by lazy {
        DaggerPresenterComponent
                .builder()
                .appComponent(LiderManApplication.appComponent)
                .build()
    }
}
