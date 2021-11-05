package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_preguntas_frecuentes_planillas.*
import kotlinx.android.synthetic.main.activity_preguntas_frecuentes_planillas.btn_chat
import java.io.Serializable
import javax.inject.Inject

class PreguntasFrecuentesPlanillasActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea1: ContactEntity? = null
    private var numberArea2: ContactEntity? = null

    private var numberAreaSelected: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_preguntas_frecuentes_planillas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Planillas", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity
        numberArea2 = intent.getSerializableExtra("extra1") as? ContactEntity


        btn_chat?.setOnClickListener {
            showDialogPlanillaNumbers()
        }


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

        btn_quinta_pregunta.setOnClickListener {
            if (see_more_quinta_pregunta.visibility == View.GONE) {
                see_more_quinta_pregunta.visibility = View.VISIBLE
            } else {
                see_more_quinta_pregunta.visibility = View.GONE
            }
        }

        btn_sexta_pregunta.setOnClickListener {
            if (see_more_sexta_pregunta.visibility == View.GONE) {
                see_more_sexta_pregunta.visibility = View.VISIBLE
            } else {
                see_more_sexta_pregunta.visibility = View.GONE
            }
        }

        btn_setima_pregunta.setOnClickListener {
            if (see_more_setima_pregunta.visibility == View.GONE) {
                see_more_setima_pregunta.visibility = View.VISIBLE
            } else {
                see_more_setima_pregunta.visibility = View.GONE
            }
        }

        btn_octava_pregunta.setOnClickListener {
            if (see_more_octava_pregunta.visibility == View.GONE) {
                see_more_octava_pregunta.visibility = View.VISIBLE
            } else {
                see_more_octava_pregunta.visibility = View.GONE
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP ) {
            numberAreaSelected?.let {
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

    private fun showDialogPlanillaNumbers() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.planilla_dialog_numbers)
        //ALL ITEMS IN THE VIEW
        val btnAtencionUno: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_atencion_uno) as AppCompatButton
        val btnAtencionDos: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_atencion_dos) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnAtencionUno.setOnClickListener {
            numberArea1?.let {
                if (!it.number.isNullOrEmpty()) {
                    numberAreaSelected = it
                    startActivityWhatssap(it.number, WHATTSAP)
                } else {
                    Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
                }
            } ?: also {
                Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
            }
        }
        btnAtencionDos.setOnClickListener {
            numberArea2?.let {
                if (!it.number.isNullOrEmpty()) {
                    numberAreaSelected = it
                    startActivityWhatssap(it.number, WHATTSAP)
                } else {
                    Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
                }
            } ?: also {
                Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }

}