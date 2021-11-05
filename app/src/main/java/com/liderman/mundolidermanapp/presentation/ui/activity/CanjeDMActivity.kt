package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import android.view.View
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_canje_d_m.*

class CanjeDMActivity : LiderManBaseActivity() {
    override fun getView(): Int = R.layout.activity_canje_d_m

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)

        btn_enfermedad_accidente.setOnClickListener {
            if (see_more_enfermedad_accidente.visibility == View.GONE) {
                see_more_enfermedad_accidente.visibility = View.VISIBLE
            } else {
                see_more_enfermedad_accidente.visibility = View.GONE
            }
        }

        btn_accidente_trabajo.setOnClickListener {
            if (see_more_accidente_trabajo.visibility == View.GONE) {
                see_more_accidente_trabajo.visibility = View.VISIBLE
            } else {
                see_more_accidente_trabajo.visibility = View.GONE
            }
        }

        btn_accidente_transito.setOnClickListener {
            if (see_more_accidente_transito.visibility == View.GONE) {
                see_more_accidente_transito.visibility = View.VISIBLE
            } else {
                see_more_accidente_transito.visibility = View.GONE
            }
        }



    }
}