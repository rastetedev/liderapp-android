package com.liderman.mundolidermanapp.presentation.ui.activity

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_ama_about.*
import kotlinx.android.synthetic.main.custom_next_button_layout.*
import kotlinx.android.synthetic.main.custom_previous_button_layout.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.CarouselOnScrollListener
import java.lang.StringBuilder

class AmaAboutActivity : LiderManBaseActivity() {
    private lateinit var amaAboutEntity: List<AmaTratoEntity>
    private lateinit var dataAmaAbout: AmaTratoEntity

    override fun getView(): Int = R.layout.activity_ama_about

    override fun onCreate() {
        setSupportActionBar(resources.getString(R.string.ama_trato), R.color.white)
        amaAboutEntity = intent.getSerializableExtra("extra0") as List<AmaTratoEntity>
        dataAmaAbout = amaAboutEntity[0]
        val list = mutableListOf<CarouselItem>()
        amaAboutEntity.forEach {
            val item = CarouselItem(
                imageUrl = "${BuildConfig.URL_BASE}${it.imageUrl}",
                caption = it.label
            )
            list.add(item)
        }
        carousel.addData(list)
        txt_caption.text = dataAmaAbout.label
        txt_caption.isSelected = true

        custom_btn_next.setOnClickListener {
            carousel.next()
        }
        custom_btn_previous.setOnClickListener {
            carousel.previous()
        }
        carousel.onScrollListener = object : CarouselOnScrollListener {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
                position: Int,
                carouselItem: CarouselItem?
            ) {
                dataAmaAbout = amaAboutEntity[position]
                txt_caption.text = dataAmaAbout.label
                txt_caption.isSelected = true
            }
        }


        val requirements = dataAmaAbout.requirements.split(".")
        val text = StringBuilder("")
        requirements.forEach {
            if (it.isNotEmpty()) {
                text.append(it + "\n")
            }
        }
        btn_requirements?.setOnClickListener {
            if (text.isNotEmpty()) {
                MaterialDialog.Builder(this)
                    .title(dataAmaAbout.label)
                    .content(text)
                    .cancelable(true)
                    .show()
            } else {
                Toast.makeText(this, R.string.ama_tratos_no_requirements, Toast.LENGTH_SHORT).show()
            }

        }
        btn_inscription?.setOnClickListener {
            if (dataAmaAbout.webRedirectInscription.isNotEmpty()) {
                startActivityUrl(dataAmaAbout.webRedirectInscription)
            } else {
                Toast.makeText(this, R.string.ama_tratos_no_inscription, Toast.LENGTH_SHORT).show()
            }
        }

        super.onCreate()
    }
}
