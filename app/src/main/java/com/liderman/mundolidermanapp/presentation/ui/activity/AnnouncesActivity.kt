package com.liderman.mundolidermanapp.presentation.ui.activity

import android.view.View
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_announces.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem

class AnnouncesActivity : LiderManBaseActivity() {

    override fun getView() = R.layout.activity_announces

    override fun onCreate() {
        super.onCreate()
        setSupportActionBar(resources.getString(R.string.option_novedades), R.color.white)
        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"
        val list = mutableListOf<CarouselItem>()

        val announcements = PapersManager.announcementsEntity

        if(announcements.isNotEmpty()){
            carousel?.visibility = View.VISIBLE
            lbl_announcements_empty?.visibility = View.GONE

            PapersManager.announcementsEntity.forEach {
                list.add(
                    CarouselItem(
                        imageUrl = "${BuildConfig.URL_BASE}${it.imageUrl}",
                        caption = ""
                    )
                )
            }
            carousel?.addData(list)
        } else {
            carousel?.visibility = View.GONE
            lbl_announcements_empty?.visibility = View.VISIBLE
        }


    }
}
