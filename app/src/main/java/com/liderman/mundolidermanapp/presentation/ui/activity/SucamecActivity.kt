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
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_sucamec.*
import java.io.Serializable
import javax.inject.Inject

class SucamecActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea1: ContactEntity? = null
    private var numberArea2: ContactEntity? = null
    private var numberArea3: ContactEntity? = null

    private var numberAreaSelected: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_sucamec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("SUCAMEC", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity
        numberArea2 = intent.getSerializableExtra("extra1") as? ContactEntity
        numberArea3 = intent.getSerializableExtra("extra2") as? ContactEntity

        btn_sucamec.setOnClickListener {
            startActivityUrl("https://drive.google.com/file/d/1PkT-LJHpBR-I9Pfd5G8wb-tvAT85-J2D/view?usp=sharing")
        }

        btn_chat.setOnClickListener {
            showDialogSucamecNumbers()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP) {
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

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }

    private fun showDialogSucamecNumbers() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.sucamec_dialog_numbers)
        //ALL ITEMS IN THE VIEW
        val btnCursoSucamec: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_curso_sucamec) as AppCompatButton
        val btnLicenciaArmas: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_licencia_armas) as AppCompatButton
        val btnRenovaciones: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_renovaciones) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()


        btnCursoSucamec.setOnClickListener {
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
        btnLicenciaArmas.setOnClickListener {
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
        btnRenovaciones.setOnClickListener {
            numberArea3?.let {
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
}