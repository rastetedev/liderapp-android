package com.liderman.mundolidermanapp.presentation.ui.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.di.Orchestrator

import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.ui.activity.SplashActivity
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication


abstract class LiderManBaseActivity : BaseActivity() {

    private var dialog: MaterialDialog? = null

    @Suppress("PropertyName")
    val WHATTSAP = 5678
    val CONTRACT = 5679

    protected val component by lazy { Orchestrator.presenterComponent }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.content, fragment).commit()

    }

    fun getContext() = this

    fun showLoading() {
        if (hasWindowFocus())
            dialog = MaterialDialog.Builder(this@LiderManBaseActivity)
                .title("Cargando...")
                .content("Espera un momento")
                .progress(true, 0)
                .cancelable(false)
                .show()
    }

    fun hideLoading() {
        if (dialog == null) return
        dialog?.dismiss()
        dialog = null
    }

    private fun getErrorDialog(message: String) = MaterialDialog.Builder(this)
        .title("Advertencia")
        .content(message)
        .positiveText("Ok")

    fun showError(message: String) {
        getErrorDialog(message).show()
    }

    fun showError(message: Int) {
        showError(getString(message))
    }

    fun showSessionFinish() {
        MaterialDialog.Builder(this)
            .title("Advertencia")
            .content("Su sessión a finalizado, vuelva a logearse")
            .cancelable(false)
            .positiveText("Ok").onPositive { _, _ ->
                PapersManager.session = false
                LiderManApplication.closeAll()
                startActivityE(SplashActivity::class.java)
            }
            .show()
    }

    fun showBottomSheetDialog(listener: (Int, Boolean) -> Unit) {
        var effective: Boolean? = null
        val list = arrayListOf("", "Muy malo", "Malo", "Bueno", "Muy bueno", "¡Excelente!")
        val view = layoutInflater.inflate(R.layout.layout_bottom_sheet, null)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        dialog.setCancelable(false)
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        val messageRating: TextView = dialog.findViewById<View>(R.id.message_rating) as TextView

        val btn1: AppCompatButton = dialog.findViewById<View>(R.id.btn_confirm) as AppCompatButton
        val btn2: AppCompatButton = dialog.findViewById<View>(R.id.btn_cancel) as AppCompatButton

        val btnYes: AppCompatButton = dialog.findViewById<View>(R.id.btn_yes) as AppCompatButton
        val btnNo: AppCompatButton = dialog.findViewById<View>(R.id.btn_no) as AppCompatButton

        val ratingBar: RatingBar = dialog.findViewById<View>(R.id.rating) as RatingBar
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating.toInt().toFloat()
            messageRating.text = list[rating.toInt()]
            val disable = (rating.toInt() == 0)
            btn1.isFocusable = !disable
            btn1.isClickable = !disable
            btn1.isEnabled = !disable
            btn1.setTextColor(
                if (!disable) ContextCompat.getColor(
                    this,
                    R.color.colorPrimary2
                ) else ContextCompat.getColor(this, R.color.disable_color)
            )
        }
        btnYes.setOnClickListener {
            effective = true
            btnYes.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary2))
            btnNo.setTextColor(ContextCompat.getColor(this, R.color.disable_color))
        }
        btnNo.setOnClickListener {
            effective = false
            btnNo.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary2))
            btnYes.setTextColor(ContextCompat.getColor(this, R.color.disable_color))
        }

        btn1.setOnClickListener {
            if (ratingBar.rating.toInt() != 0) {
                if (effective != null) {
                    listener(ratingBar.rating.toInt(), effective!!)
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "¿Sus dudas fueron resueltas?", Toast.LENGTH_SHORT).show()
                }
            }
        }
        btn2.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}