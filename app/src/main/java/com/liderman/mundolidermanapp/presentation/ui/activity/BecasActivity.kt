package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_becas.*

class BecasActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_becas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(resources.getString(R.string.ama_crece), R.color.white)

        btn_requisitos.setOnClickListener { showDialog() }

        btn_sorteo.setOnClickListener { startActivityUrl("https://forms.gle/GsDCoFnn1DcxqC3n6") }

        btn_ganadores.setOnClickListener { startActivityUrl("https://docs.google.com/presentation/d/1YLSdGLBL1VVxrSgOSkobyrxnCz4ekKi4J49y0Pq5XL0/edit?usp=sharing") }
    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_requisitos_becas)

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()
    }
}