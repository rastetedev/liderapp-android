package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_financiamiento.*

class FinanciamientoActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_financiamiento

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(resources.getString(R.string.ama_crece), R.color.white)

        btn_requisitos.setOnClickListener { showDialog() }

        btn_convenios.setOnClickListener { startActivityE(ConveniosFinanciamientoActivity::class.java) }

        btn_certificados.setOnClickListener { startActivityUrl("https://mundoliderman.com/amacrece/Financiamiento/Certificados") }

    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_requisitos_financiamiento)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()
    }
}