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
import kotlinx.android.synthetic.main.activity_licencia.*
import kotlinx.android.synthetic.main.activity_licencia.btn_chat
import java.io.Serializable
import javax.inject.Inject

class LicenciaActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea2: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_licencia

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea2 = intent.getSerializableExtra("extra0") as? ContactEntity

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

        btn_dad.setOnClickListener {
            if (see_more_paternidad.visibility == View.GONE) {
                see_more_paternidad.visibility = View.VISIBLE
            } else {
                see_more_paternidad.visibility = View.GONE
            }
        }

        btn_death.setOnClickListener {
            if (see_more_luto.visibility == View.GONE) {
                see_more_luto.visibility = View.VISIBLE
            } else {
                see_more_luto.visibility = View.GONE
            }
        }

        btn_hearth.setOnClickListener {
            if (see_more_familiar_directo.visibility == View.GONE) {
                see_more_familiar_directo.visibility = View.VISIBLE
            } else {
                see_more_familiar_directo.visibility = View.GONE
            }
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

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }
}