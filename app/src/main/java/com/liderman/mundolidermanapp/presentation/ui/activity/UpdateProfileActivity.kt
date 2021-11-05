package com.liderman.mundolidermanapp.presentation.ui.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import com.bumptech.glide.Glide
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.data.request.UpdateProfileRequest
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import com.liderman.mundolidermanapp.utils.*
import com.liderman.mundolidermanapp.presentation.presenter.ProfilePresenter
import com.liderman.mundolidermanapp.presentation.ui.base.UpdateBaseActivity
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.btn_camera
import kotlinx.android.synthetic.main.activity_update_profile.id_number_badges
import kotlinx.android.synthetic.main.activity_update_profile.id_points
import kotlinx.android.synthetic.main.activity_update_profile.image_profile
import kotlinx.android.synthetic.main.activity_update_profile.txt_document
import kotlinx.android.synthetic.main.activity_update_profile.txt_name_user
import javax.inject.Inject

class UpdateProfileActivity : UpdateBaseActivity(), ProfilePresenter.View {

    @Inject
    lateinit var profilePresenter: ProfilePresenter

    override fun getView(): Int = R.layout.activity_update_profile

    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        setSupportActionBar("Actualizar Perfil", R.color.white)

        with(PapersManager.loginEntity) {
            this@UpdateProfileActivity.txt_document.text = getString(R.string.user_dni, this.dni)
            this@UpdateProfileActivity.txt_unidad.text = getString(R.string.user_unidad, this.unidad)
            this@UpdateProfileActivity.txt_zona.text = getString(R.string.user_zona, this.zona)
            this@UpdateProfileActivity.txt_name_user.text = "${this.names} ${this.lastNames}"
            this@UpdateProfileActivity.txt_email_update.setText(this.email)
            this@UpdateProfileActivity.txt_phone_update.setText(this.phone)
            this@UpdateProfileActivity.txt_address_update.setText(this.address)
            this@UpdateProfileActivity.id_points.text = "${this.points ?: 0} pts"
            this@UpdateProfileActivity.id_number_badges.text = this.numberBadges ?: "0"

            Glide.with(this@UpdateProfileActivity)
                .load(if (this.photoUrl.isNullOrEmpty()) R.drawable.user_profile else (BuildConfig.URL_BASE + this.photoUrl))
                .circleCrop()
                .into(this@UpdateProfileActivity.image_profile)
        }
        btn_camera.setOnClickListener {
            selectOption()
        }
        activeAllWrappers()
        btn_update.setOnClickListener {
            valid()
        }
    }

    override fun onResume() {
        super.onResume()
        profilePresenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        profilePresenter.detachView()
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
            else -> {
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_PHOTOS) {
            ProcessBitmap(object : ProcessBitmap.DoStuff {
                override fun getContext() = this@UpdateProfileActivity
                override fun done(s: String) {
                    profilePresenter.uploadImage(s)
                }
            }).execute(data!!.extras!!.get("data") as Bitmap, true)//true is for camera
        }
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            ProcessBitmap(object : ProcessBitmap.DoStuff {
                override fun getContext() = this@UpdateProfileActivity
                override fun done(s: String) {
                    profilePresenter.uploadImage(s)
                }
            }).execute(data?.data.toString(), false)//false is for gallery
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun valid() {

        if (txt_email_update.isNotEmpty() || txt_phone_update.isNotEmpty() || txt_address_update.isNotEmpty()) {
            val updateData = UpdateProfileRequest(
                PapersManager.loginEntity.userId,
                txt_phone_update.getString(),
                txt_email_update.getString(),
                txt_address_update.getString()
            )
            profilePresenter.updateProfile(updateData)
        } else {
            Toast.makeText(this, R.string.error_profile_update, Toast.LENGTH_SHORT).show()
        }

    }

    override fun successProfilePresenter(status: Int, loginEntity: LoginEntity) {
        when (status) {
            208 -> {
                Glide.with(this@UpdateProfileActivity)
                    .load((BuildConfig.URL_BASE + loginEntity.photoUrl))
                    .circleCrop()
                    .into(image_profile)
            }
            202 -> Toast.makeText(this, "Datos actualizados", Toast.LENGTH_LONG).show()
        }
    }

    override fun successLogout(success: Boolean) {
        //
    }
}