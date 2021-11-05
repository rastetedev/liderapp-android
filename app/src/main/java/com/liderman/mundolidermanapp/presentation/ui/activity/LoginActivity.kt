package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.getString
import com.liderman.mundolidermanapp.utils.isEmpty
import com.liderman.mundolidermanapp.utils.showError
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.LoginPresenter
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : LiderManBaseActivity(), LoginPresenter.View {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun getView(): Int = R.layout.activity_login

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        loginPresenter.attachView(this)

        activeAllWrappers()

        btn_login?.setOnClickListener {
            valid()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginPresenter.detachView()
    }

    override fun successLogin() {
        LiderManApplication.closeAll()
        startActivityE(MainActivity::class.java)
    }

    private fun valid() {
        hideAllWrappers()

        if (user.isEmpty() && password.isEmpty()) return

        if (user.isEmpty()) {
            user_wrapper.showError(resources.getString(R.string.field_required))
            return
        }

        if (password.isEmpty()) {
            password_wrapper.showError(resources.getString(R.string.field_required))
            return
        }

        //Firebase
        val analytic = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString("user",user.getString())
        analytic.logEvent("InitLogin",bundle)
        loginPresenter.makeLogin(user.getString(), password.getString())
    }

}
