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
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_renuncia.*
import kotlinx.android.synthetic.main.activity_renuncia.btn_chat
import java.io.Serializable
import javax.inject.Inject

class RenunciaActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea2: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_renuncia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Renuncias", R.color.white)

        component.inject(this)
        contactPresenter.attachView(this)

        numberArea2 = intent.getSerializableExtra("extra0") as? ContactEntity


        btn_preguntas_frecuentes?.setOnClickListener {
            startActivityE(PreguntasFrecuentesRenunciaActivity::class.java)
        }

        btn_renuncia?.setOnClickListener { showDialog() }

        btn_detalle_del_proceso?.setOnClickListener {
            startActivityE(DetalleProcesoActivity::class.java)
        }

        btn_modelo_de_carta?.setOnClickListener {
            startActivityUrl("https://docs.google.com/document/d/1iuVvTlgIiMekmXRRxJRgfcTSxu6In7fwhDQbkJYXf9s/edit?usp=sharing")
        }

        numberArea2?.let { contact ->

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


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP) {

            numberArea2?.let {
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


    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_atencion_renuncia)

        //Los items del pop up
        val btnCancelar: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_cancelar_renuncia) as AppCompatButton
        val btnContinuarRenuncia: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_continuar_renuncia) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnCancelar.setOnClickListener { dialog.dismiss() }

        btnContinuarRenuncia.setOnClickListener {
            startActivityE(FormRenunciasActivity::class.java)
            dialog.dismiss()
        }


    }

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }

}