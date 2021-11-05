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
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_descanso_medico.*
import kotlinx.android.synthetic.main.activity_descanso_medico.btn_chat
import java.io.Serializable
import javax.inject.Inject

class DescansoMedicoActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea1: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_descanso_medico

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity

        numberArea1?.let { contact ->

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

        btn_presentar_dm.setOnClickListener {
            startActivityE(PresentarDMActivity::class.java)
        }

        btn_canje_dm.setOnClickListener {
            showDialog()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP ) {
            numberArea1?.let {
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


    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.canje_dm_dialog)

        //Los items del pop up
        val btnCancelar: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_cancelar) as AppCompatButton
        val btnContinuarCanje: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_continuar_canje) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnCancelar.setOnClickListener { dialog.dismiss() }

        btnContinuarCanje.setOnClickListener {
            startActivityE(CanjeDMActivity::class.java)
            dialog.dismiss()
        }
    }
}