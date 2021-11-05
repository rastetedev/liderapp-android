package com.liderman.mundolidermanapp.presentation.ui.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.MaterialDialog
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.di.Orchestrator
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.ui.activity.SplashActivity
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication


abstract class LiderManBaseFragment : BaseFragment() {

    private var dialog: MaterialDialog? = null

    override fun getContext(): Context = this.activity?.applicationContext!!

    protected val component by lazy { Orchestrator.presenterComponent }

    fun showLoading() {
        hideLoading()
        dialog = MaterialDialog.Builder(activity!!)
            .title("Conectando...")
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

    fun showError(message: String) {
        MaterialDialog.Builder(this.activity!!)
            .title("Advertencia")
            .content(message)
            .positiveText("Ok")
            .show()
    }

    fun replaceFragment(fragment: Fragment) {
        fragmentManager!!.beginTransaction().replace(R.id.content, fragment).commit()
    }

    fun showError(message: Int) {
        showError(getString(message))
    }

    fun showSessionFinish() {
        MaterialDialog.Builder(this.activity!!)
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

}