package com.liderman.mundolidermanapp.presentation.ui.fragment

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.utils.ProcessBitmap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.EventPresenter

import com.liderman.mundolidermanapp.presentation.presenter.ProfilePresenter
import com.liderman.mundolidermanapp.presentation.ui.activity.MisArchivosActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.ShareFriendsActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.UpdateProfileActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.ClaimActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.MainActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.MyTicketsActivity
import com.liderman.mundolidermanapp.presentation.ui.activity.LoginActivity
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication
import com.liderman.mundolidermanapp.presentation.ui.base.ProfileBaseFragment
import com.liderman.mundolidermanapp.utils.PapersManager
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : ProfileBaseFragment(), ProfilePresenter.View, EventPresenter.View {

    @Inject
    lateinit var profilePresenter: ProfilePresenter

    @Inject
    lateinit var eventPresenter: EventPresenter

    override fun getFragmentView(): Int = R.layout.fragment_profile

    override fun onCreate() {
        (activity as MainActivity).setToolbarTitle(getString(R.string.profile))
        component.inject(this)



        btn_close_session?.setOnClickListener {
            closeSession()
        }
        btn_camera?.setOnClickListener {
            selectOption()
        }

        btn_update_info?.setOnClickListener {
            startActivityE(UpdateProfileActivity::class.java)
        }
        btn_share_friend?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitRecomiendaUnAmigo", bundle)
            eventPresenter.sendEvent("InitRecomiendaUnAmigo")
            startActivityE(ShareFriendsActivity::class.java)
        }
        btn_claim?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitReclamos", bundle)
            eventPresenter.sendEvent("InitReclamos")
            startActivityE(ClaimActivity::class.java)
        }
        btn_mis_archivos?.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitMisArchivos", bundle)
            eventPresenter.sendEvent("InitMisArchivos")
            startActivityE(MisArchivosActivity::class.java)
        }
        btn_tickets?.setOnClickListener {
            startActivityE(MyTicketsActivity::class.java)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentCamera()
                }
            }
            PERMISSION_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentGallery()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profilePresenter.attachView(this)
        eventPresenter.attachView(this)
        profilePresenter.getProfile()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        profilePresenter.detachView()
        eventPresenter.detachView()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_PHOTOS) {
            ProcessBitmap(object : ProcessBitmap.DoStuff {
                override fun getContext() = activity!!
                override fun done(s: String) {
                    profilePresenter.uploadImage(s)
                }
            }).execute(data!!.extras!!.get("data") as Bitmap, true)//true is for camera
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            ProcessBitmap(object : ProcessBitmap.DoStuff {
                override fun getContext() = activity!!
                override fun done(s: String) {
                    profilePresenter.uploadImage(s)
                }
            }).execute(data?.data.toString(), false)//false is for gallery
        }
    }

    private fun closeSession() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_close_session)
        val btnConfirm: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_confirm) as AppCompatButton
        val btnCancel: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_cancel) as AppCompatButton
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()
        btnConfirm.setOnClickListener {
            val analytic = FirebaseAnalytics.getInstance(context)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitCerrarSesion", bundle)
            dialog.dismiss()
            eventPresenter.sendEvent("InitCerrarSesion")
            profilePresenter.logout()
        }
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun successLogout(success: Boolean) {
        LiderManApplication.closeAll()
        startActivityE(LoginActivity::class.java)
    }

    override fun successProfilePresenter(status: Int, loginEntity: LoginEntity) {

        with(loginEntity) {
            this@ProfileFragment.txt_name_user.text =
                getString(R.string.user_name, "${this.names} ${this.lastNames}")
            this@ProfileFragment.txt_document.text = getString(R.string.user_dni, this.dni)
            this@ProfileFragment.txt_unidad.text = getString(R.string.user_unidad, this.unidad)
            this@ProfileFragment.txt_zona.text = getString(R.string.user_zona, this.zona)
            this@ProfileFragment.id_points.text =
                getString(R.string.user_points, "${this.points ?: 0}")
            this@ProfileFragment.id_number_badges.text = this.numberBadges ?: "0"

            Glide.with(this@ProfileFragment)
                .load(if (this.photoUrl.isNullOrEmpty()) R.drawable.user_profile else (BuildConfig.URL_BASE + this.photoUrl))
                .circleCrop()
                .into(this@ProfileFragment.image_profile)
        }
    }
}
