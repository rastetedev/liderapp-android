package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_accidente_de_trabajo.*
import kotlinx.android.synthetic.main.activity_accidente_de_trabajo.btn_chat
import java.io.Serializable
import javax.inject.Inject

class AccidenteDeTrabajoActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea5: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_accidente_de_trabajo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea5 = intent.getSerializableExtra("extra0") as? ContactEntity

        btn_que_accidente_trabajo.setOnClickListener {
            if (see_more_que_accidente_trabajo.visibility == View.GONE) {
                see_more_que_accidente_trabajo.visibility = View.VISIBLE
            } else {
                see_more_que_accidente_trabajo.visibility = View.GONE
            }
        }

        btn_informar_accidente_trabajo.setOnClickListener {
            if (see_more_informar_accidente_trabajo.visibility == View.GONE) {
                see_more_informar_accidente_trabajo.visibility = View.VISIBLE
            } else {
                see_more_informar_accidente_trabajo.visibility = View.GONE
            }
        }

        btn_donde_accidente_transito.setOnClickListener {
            if (see_more_donde_accidente_transito.visibility == View.GONE) {
                see_more_donde_accidente_transito.visibility = View.VISIBLE
            } else {
                see_more_donde_accidente_transito.visibility = View.GONE
            }
        }

        numberArea5?.let { contact ->

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
            numberArea5?.let {
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