package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import android.util.TypedValue
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import com.liderman.mundolidermanapp.utils.Methods
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.AmaPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import com.liderman.mundolidermanapp.utils.PapersManager
import kotlinx.android.synthetic.main.activity_lidermania.*
import java.io.Serializable
import javax.inject.Inject

class LidermaniaActivity : LiderManBaseActivity(), AmaPresenter.View {

    @Inject
    lateinit var amaPresenter: AmaPresenter
    private var type1: Boolean = true
    private var type2: Boolean = true
    private var type3: Boolean = true
    private var type4: Boolean = true
    private var widthScreen: Int = 0
    private var floatOpen: Float = 0.0F
    private var floatClose: Float = 0.0F
    private var textSizeDynamic: Float = 0.0f
    private lateinit var linears: ArrayList<TextView>

    override fun getView(): Int = R.layout.activity_lidermania

    override fun onCreate() {
        super.onCreate()
        setSupportActionBar(resources.getString(R.string.lidermania), R.color.white)
        component.inject(this)
        linears = arrayListOf(btn_one, btn_two, btn_three, btn_four)
        val widthScreenBase = Methods.getWidthScreen()
        widthScreen = (widthScreenBase * 1.2).toInt()
        floatClose = ((widthScreen / 3.1) * 1).toFloat()
        floatOpen = ((widthScreen / 336.0) * 1).toFloat() * -1

        textSizeDynamic = when {
            widthScreenBase >= 360 -> 22f
            widthScreenBase in 361..640 -> 22F
            widthScreenBase in 640..720 -> 22F
            else -> 22F
        }

        linears.forEach {
            it.layoutParams.apply {
                width = (widthScreen / 2)
                height = ((widthScreen / 2) * 0.37).toInt()
            }
            it.setPadding((widthScreen / 2) / 2.95.toInt(), 0, 0, 0)
            it.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeDynamic)//336 - 330
            it.translationX = floatClose
        }

        btn_one.setOnClickListener {
            btn_one.animate().translationX(if (type1) floatOpen else floatClose).duration = 500
            type1 = !type1
            if (type1) {
                amaPresenter.getAmaAbout()
            }
        }

        btn_two.setOnClickListener {
            btn_two.animate().translationX(if (type2) floatOpen else floatClose).duration = 500
            type2 = !type2
            if (type2) {
                amaPresenter.getAmaPay()
            }
        }

        btn_three.setOnClickListener {
            btn_three.animate().translationX(if (type3) floatOpen else floatClose).duration = 500
            type3 = !type3
            if (type3) {
                amaPresenter.getAmaLife()
            }
        }

        btn_four.setOnClickListener {
            btn_four.animate().translationX(if (type4) floatOpen else floatClose).duration = 500
            type4 = !type4
            if (type4) {
                val analytic = FirebaseAnalytics.getInstance(this)
                val bundle = Bundle()
                bundle.putString("zona", PapersManager.loginEntity.zona)
                bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                bundle.putString("genero", PapersManager.loginEntity.sexo)
                bundle.putString("edad", PapersManager.loginEntity.edad)
                bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                analytic.logEvent("InitAmaCrece", bundle)
                startActivityE(AmaCreceActivity::class.java)
            }
        }

        btn_culture.setOnClickListener {
            startActivityUrl(getString(R.string.pasaporte_link))
        }
    }

    override fun onResume() {
        super.onResume()
        amaPresenter.attachView(this)
    }

    override fun onPause() {
        btn_one.translationX = floatClose
        btn_two.translationX = floatClose
        btn_three.translationX = floatClose
        btn_four.translationX = floatClose
        type1 = true
        type2 = true
        type3 = true
        type4 = true
        amaPresenter.detachView()
        super.onPause()
    }

    override fun successAmaLife(amaLifes: List<AmaLifeEntity>) {
        if (amaLifes.isNotEmpty()) {
            val analytic = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitAmaVida", bundle)
            startActivityE(AmaLifeActivity::class.java, amaLifes as Serializable)
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.no_ama_lifes),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun successAmaPay(amaPays: List<AmaPayEntity>) {
        if (amaPays.isNotEmpty()) {
            val analytic = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitAmaPago", bundle)
            startActivityE(AmaPayActivity::class.java, amaPays as Serializable)
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.no_ama_pays),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun successAmaTrato(amaTratos: List<AmaTratoEntity>) {
        if (amaTratos.isNotEmpty()) {
            val analytic = FirebaseAnalytics.getInstance(this)
            val bundle = Bundle()
            bundle.putString("zona", PapersManager.loginEntity.zona)
            bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
            bundle.putString("genero", PapersManager.loginEntity.sexo)
            bundle.putString("edad", PapersManager.loginEntity.edad)
            bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
            analytic.logEvent("InitAmaTrato", bundle)
            startActivityE(AmaAboutActivity::class.java, amaTratos as Serializable)
        } else {
            Toast.makeText(
                this,
                resources.getString(R.string.no_ama_tratos),
                Toast.LENGTH_SHORT
            ).show()
        }

    }
}