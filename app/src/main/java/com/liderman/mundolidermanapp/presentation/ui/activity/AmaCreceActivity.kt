package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import android.view.View
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_ama_crece.*

class AmaCreceActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_ama_crece

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(resources.getString(R.string.ama_crece), R.color.white)

        btn_logo.setOnClickListener {
            startActivityUrl("https://mundoliderman.com/amacrece/")
        }

        btn_financiamiento.setOnClickListener {
            if (see_more_finaciamiento.visibility == View.GONE) {
                see_more_finaciamiento.visibility = View.VISIBLE
            } else {
                see_more_finaciamiento.visibility = View.GONE
            }
        }

        see_more_finaciamiento.setOnClickListener { startActivityE(FinanciamientoActivity::class.java) }

        btn_becas.setOnClickListener {
            if (see_more_becas.visibility == View.GONE) {
                see_more_becas.visibility = View.VISIBLE
            } else {
                see_more_becas.visibility = View.GONE
            }
        }

        see_more_becas.setOnClickListener { startActivityE(BecasActivity::class.java) }

        btn_premium.setOnClickListener {
            if (see_more_premium.visibility == View.GONE) {
                see_more_premium.visibility = View.VISIBLE
            } else {
                see_more_premium.visibility = View.GONE
            }
        }

        see_more_premium.setOnClickListener { startActivityE(PremiumActivity::class.java) }

        btn_preguntas.setOnClickListener {
            startActivityUrl("https://mundoliderman.com/amacrece/PreguntasFrecuentes/")
        }

        btn_quiero_crecer.setOnClickListener { startActivityE(QuieroCrecerActivity::class.java) }


    }


}