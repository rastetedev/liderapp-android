package com.liderman.mundolidermanapp.presentation.ui.activity

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.liderman.mundolidermanapp.BuildConfig
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_ama_life.btn_inscription
import kotlinx.android.synthetic.main.activity_ama_life.btn_requirements
import kotlinx.android.synthetic.main.activity_ama_life.carousel
import kotlinx.android.synthetic.main.activity_ama_life.txt_caption
import kotlinx.android.synthetic.main.custom_next_button_layout.*
import kotlinx.android.synthetic.main.custom_previous_button_layout.*
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.CarouselOnScrollListener
import java.lang.StringBuilder

class AmaLifeActivity : LiderManBaseActivity() {
    private lateinit var amaLifeEntity: List<AmaLifeEntity>
    private lateinit var dataAmaLife: AmaLifeEntity

    override fun getView(): Int = R.layout.activity_ama_life

    override fun onCreate() {
        setSupportActionBar(resources.getString(R.string.ama_vida), R.color.white)
        amaLifeEntity = intent.getSerializableExtra("extra0") as List<AmaLifeEntity>
        dataAmaLife = amaLifeEntity[0]
        val list = mutableListOf<CarouselItem>()
        amaLifeEntity.forEach {
            val item = CarouselItem(
                imageUrl = "${BuildConfig.URL_BASE}${it.imageUrl}",
                caption = it.label
            )
            list.add(item)
        }
        carousel.addData(list)
        txt_caption.text = dataAmaLife.label
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
                dataAmaLife = amaLifeEntity[position]
                txt_caption.text = dataAmaLife.label
                txt_caption.isSelected = true
            }
        }

        val requirements = dataAmaLife.requirements.split(".")
        val text = StringBuilder("")
        requirements.forEach {
            if(it.isNotEmpty()){
                text.append(it + "\n")
            }
        }
        btn_requirements?.setOnClickListener {
            if (text.isNotEmpty()) {
                MaterialDialog.Builder(this)
                    .title(dataAmaLife.label)
                    .content(text)
                    .cancelable(true)
                    .show()
            } else {
                Toast.makeText(this, R.string.ama_lifes_no_requirements, Toast.LENGTH_SHORT).show()
            }

        }
        btn_inscription.setOnClickListener {
            if(dataAmaLife.webRedirectInscription.isNotEmpty()) {
                startActivityUrl(dataAmaLife.webRedirectInscription)
            } else {
                Toast.makeText(this, R.string.ama_lifes_no_inscription, Toast.LENGTH_SHORT).show()
            }

        }

        super.onCreate()
    }
}
