package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_premium.*

class PremiumActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_premium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(resources.getString(R.string.ama_crece), R.color.white)

        btn_requisitos.setOnClickListener { showDialog() }

        btn_postula.setOnClickListener { startActivityUrl("https://forms.gle/F9vUks9Hes84wwNMA") }

        btn_testimonios.setOnClickListener { startActivityUrl("https://mundoliderman.com/amacrece/Testimonios/") }
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_requisitos_premium)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()
    }
}