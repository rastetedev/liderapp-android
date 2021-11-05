package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_uniformes.*
import java.io.Serializable
import javax.inject.Inject

class UniformesActivity : LiderManBaseActivity(), ContactPresenter.View {
    @Inject
    lateinit var contactPresenter: ContactPresenter

    override fun getView(): Int = R.layout.activity_uniformes

    private var numberArea: ContactEntity? = null

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Uniformes", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea = intent.getSerializableExtra("extra0") as? ContactEntity

        numberArea?.let { contact ->

            btn_chat.setOnClickListener {
                if (!contact.number.isNullOrEmpty()) {
                    startActivityWhatssap(contact.number, WHATTSAP)
                } else {
                    Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
                }
            }
        } ?: also {
            Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
        }

        btn_procedimiento_uniforme.setOnClickListener {
            startActivityE(ProcedimientoUniformeActivity::class.java)
        }


        btn_genera_solicitud.setOnClickListener {
            startActivityE(
                SolicitudUniformeActivity::class.java,
                numberArea as Serializable
            )
        }

        btn_frecuentes_uniformes.setOnClickListener {
            startActivityE(
                PreguntasFrecuentesUniformeActivity::class.java,
                numberArea as Serializable
            )
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