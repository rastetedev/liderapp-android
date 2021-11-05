package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_solicitud_uniforme.*
import kotlinx.android.synthetic.main.activity_solicitud_uniforme.btn_chat
import java.io.Serializable
import javax.inject.Inject

class SolicitudUniformeActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    override fun getView(): Int = R.layout.activity_solicitud_uniforme

    private var numberArea: ContactEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setSupportActionBar("Genera tu solicitud", R.color.white)

        numberArea = intent.getSerializableExtra("extra0") as? ContactEntity

        btn_renovacion_individual.setOnClickListener {
            startActivityUrl("https://docs.google.com/forms/d/e/1FAIpQLSfqOgbe-OAzQqe2JDRHbkfyt1B5Y2UhNmNEnEIUeIrO7RjYCw/viewform")
        }


        btn_renovacion_masiva.setOnClickListener {
            startActivityUrl("https://docs.google.com/spreadsheets/d/1LD6U9-TN8BdBCdvMmAEpUAqPP3wyuL57JfDh-hcDAn8/edit?usp=sharing")
        }


        numberArea?.let { contact ->

            btn_chat.setOnClickListener {
                if (contact.number.isNotEmpty()) {
                    startActivityWhatssap(contact.number, WHATTSAP)
                } else {
                    Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP) {
            numberArea?.let {
                showBottomSheetDialog { serviceStars, effective ->
                    contactPresenter.qualityServices(
                        QualityRequest(
                            PapersManager.loginEntity.userId,
                            it.id,
                            serviceStars,
                            effective
                        )
                    )
                }
            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }


}