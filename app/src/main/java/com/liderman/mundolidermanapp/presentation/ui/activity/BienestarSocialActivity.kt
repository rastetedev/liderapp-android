package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_bienestar_social.*
import java.io.Serializable

class BienestarSocialActivity : LiderManBaseActivity() {

    private var numberArea1: ContactEntity? = null
    private var numberArea2: ContactEntity? = null
    private var numberArea3: ContactEntity? = null
    private var numberArea4: ContactEntity? = null
    private var numberArea5: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_bienestar_social

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity
        numberArea2 = intent.getSerializableExtra("extra1") as? ContactEntity
        numberArea3 = intent.getSerializableExtra("extra2") as? ContactEntity
        numberArea4 = intent.getSerializableExtra("extra3") as? ContactEntity
        numberArea5 = intent.getSerializableExtra("extra4") as? ContactEntity


        btn_rest.setOnClickListener {
            startActivityE(
                DescansoMedicoActivity::class.java,
                numberArea1 as Serializable
            )
        }
        btn_license.setOnClickListener {
            startActivityE(
                LicenciaActivity::class.java,
                numberArea2 as Serializable
            )
        }
        btn_membership.setOnClickListener {
            startActivityE(
                AfiliacionesActivity::class.java,
                numberArea3 as Serializable
            )
        }
        btn_economic.setOnClickListener {
            startActivityE(
                EvaluacionApoyoActivity::class.java
            )
        }
        btn_work.setOnClickListener {
            startActivityE(
                AccidenteDeTrabajoActivity::class.java,
                numberArea5 as Serializable
            )
        }
    }


}