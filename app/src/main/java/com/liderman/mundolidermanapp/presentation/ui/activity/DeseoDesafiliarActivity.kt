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
import kotlinx.android.synthetic.main.activity_deseo_desafiliar.*
import kotlinx.android.synthetic.main.activity_deseo_desafiliar.btn_chat
import java.io.Serializable
import javax.inject.Inject

class DeseoDesafiliarActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea3: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_deseo_desafiliar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)

        component.inject(this)
        contactPresenter.attachView(this)

        numberArea3 = intent.getSerializableExtra("extra0") as? ContactEntity

        btn_conyugue_des.setOnClickListener {
            if (see_more_conyugue_des.visibility == View.GONE) {
                see_more_conyugue_des.visibility = View.VISIBLE
            } else {
                see_more_conyugue_des.visibility = View.GONE
            }
        }

        btn_conviviente_des.setOnClickListener {
            if (see_more_conviviente_des.visibility == View.GONE) {
                see_more_conviviente_des.visibility = View.VISIBLE
            } else {
                see_more_conviviente_des.visibility = View.GONE
            }
        }

        btn_hijo_menor_des.setOnClickListener {
            if (see_more_hijo_menor_des.visibility == View.GONE) {
                see_more_hijo_menor_des.visibility = View.VISIBLE
            } else {
                see_more_hijo_menor_des.visibility = View.GONE
            }
        }

        btn_mayor_discapacidad_des.setOnClickListener {
            if (see_more_hijo_mayor_des.visibility == View.GONE) {
                see_more_hijo_mayor_des.visibility = View.VISIBLE
            } else {
                see_more_hijo_mayor_des.visibility = View.GONE
            }
        }

        btn_gestante_des.setOnClickListener {
            if (see_more_gestante_des.visibility == View.GONE) {
                see_more_gestante_des.visibility = View.VISIBLE
            } else {
                see_more_gestante_des.visibility = View.GONE
            }
        }

        numberArea3?.let { contact ->
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
        if (requestCode == WHATTSAP ) {
            numberArea3?.let {
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