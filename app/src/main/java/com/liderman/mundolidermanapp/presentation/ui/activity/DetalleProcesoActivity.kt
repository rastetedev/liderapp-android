package com.liderman.mundolidermanapp.presentation.ui.activity

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity

class DetalleProcesoActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_detalle_proceso

    override fun onCreate() {
        setSupportActionBar("Renuncias", R.color.white)
        super.onCreate()
    }
}