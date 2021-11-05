package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_preguntas_frecuentes_uniforme.*
import kotlinx.android.synthetic.main.activity_preguntas_frecuentes_uniforme.btn_chat
import java.io.Serializable
import javax.inject.Inject

class PreguntasFrecuentesUniformeActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_preguntas_frecuentes_uniforme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setSupportActionBar("Uniformes", R.color.white)

        numberArea = intent.getSerializableExtra("extra0") as? ContactEntity

        btn_primera_pregunta.setOnClickListener {
            if (see_more_primera_pregunta.visibility == View.GONE) {
                see_more_primera_pregunta.visibility = View.VISIBLE
            } else {
                see_more_primera_pregunta.visibility = View.GONE
            }
        }

        btn_segunda_pregunta.setOnClickListener {
            if (see_more_segunda_pregunta.visibility == View.GONE) {
                see_more_segunda_pregunta.visibility = View.VISIBLE
            } else {
                see_more_segunda_pregunta.visibility = View.GONE
            }
        }

        btn_tercera_pregunta.setOnClickListener {
            if (see_more_tercera_pregunta.visibility == View.GONE) {
                see_more_tercera_pregunta.visibility = View.VISIBLE
            } else {
                see_more_tercera_pregunta.visibility = View.GONE
            }
        }

        btn_cuarta_pregunta.setOnClickListener {
            if (see_more_cuarta_pregunta.visibility == View.GONE) {
                see_more_cuarta_pregunta.visibility = View.VISIBLE
            } else {
                see_more_cuarta_pregunta.visibility = View.GONE
            }
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