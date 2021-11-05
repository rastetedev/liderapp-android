package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity

class ProcedimientoUniformeActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_procedimiento_uniforme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_procedimiento_uniforme)
    }


}