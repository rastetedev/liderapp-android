package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import com.liderman.mundolidermanapp.presentation.presenter.MasterPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import com.liderman.mundolidermanapp.presentation.ui.fragment.HomeFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.PlayFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.ProfileFragment
import com.liderman.mundolidermanapp.presentation.ui.fragment.TrafficFragment
import com.liderman.mundolidermanapp.utils.PapersManager
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : LiderManBaseActivity(), MasterPresenter.View {

    @Inject
    lateinit var masterPresenter: MasterPresenter

    private var fragments = ArrayList<Fragment>()
    private var current = 0

    override fun getView(): Int = R.layout.activity_main

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        masterPresenter.attachView(this)

        masterPresenter.getAnnouncements()
        masterPresenter.getContactAreas()

        fragments.apply {
            add(HomeFragment())
            add(TrafficFragment())
            add(PlayFragment())
            add(ProfileFragment())
        }
        replaceFragment(fragments[current])

        nav_view_custom?.setItemSelected(R.id.navigation_home, true)
        nav_view_custom?.setOnItemSelectedListener { id ->
            when (id) {
                R.id.navigation_home -> current = 0
                R.id.navigation_dashboard -> current = 1
                R.id.navigation_play ->{
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitLidermanPlay", bundle)
                    current = 2
                }
                R.id.navigation_person -> current = 3
            }
            replaceFragment(fragments[current])
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        masterPresenter.detachView()
    }

    fun setToolbarTitle(title: String) {
        toolbar_title?.text = title
    }

    override fun successAnnouncements(announcements: List<AnnouncementEntity>) {

        for (announcement in announcements) {
            if (announcement.isMain == 1) {
                showMainAnnounceDialog(announcement)
                return
            }
        }
    }

    private fun showMainAnnounceDialog(announcement: AnnouncementEntity) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_announce)
        val announcementImage: AppCompatImageView =
            dialog.findViewById<View>(R.id.image_announce) as AppCompatImageView
        val btnCloseAnnounce: AppCompatImageButton =
            dialog.findViewById<View>(R.id.btn_close_announce) as AppCompatImageButton
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        Glide.with(this)
            .load("${BuildConfig.URL_BASE}${announcement.imageUrl}")
            .into(announcementImage)

        btnCloseAnnounce.setOnClickListener {
            dialog.dismiss()
        }
    }

}
