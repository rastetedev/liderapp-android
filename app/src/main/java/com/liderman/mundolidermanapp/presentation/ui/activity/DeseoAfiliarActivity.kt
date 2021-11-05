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
import kotlinx.android.synthetic.main.activity_deseo_afiliar.*
import kotlinx.android.synthetic.main.activity_deseo_afiliar.btn_chat
import java.io.Serializable
import javax.inject.Inject

class DeseoAfiliarActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea3: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_deseo_afiliar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deseo_afiliar)
        setSupportActionBar("Bienestar Social", R.color.white)

        component.inject(this)
        contactPresenter.attachView(this)

        numberArea3 = intent.getSerializableExtra("extra0") as? ContactEntity

        btn_conyugue.setOnClickListener {
            if (see_more_conyugue.visibility == View.GONE) {
                see_more_conyugue.visibility = View.VISIBLE
            } else {
                see_more_conyugue.visibility = View.GONE
            }
        }

        btn_conviviente.setOnClickListener {
            if (see_more_conviviente.visibility == View.GONE) {
                see_more_conviviente.visibility = View.VISIBLE
            } else {
                see_more_conviviente.visibility = View.GONE
            }
        }

        btn_hijo_menor.setOnClickListener {
            if (see_more_hijo_menor.visibility == View.GONE) {
                see_more_hijo_menor.visibility = View.VISIBLE
            } else {
                see_more_hijo_menor.visibility = View.GONE
            }
        }

        btn_mayor_discapacidad.setOnClickListener {
            if (see_more_hijo_mayor.visibility == View.GONE) {
                see_more_hijo_mayor.visibility = View.VISIBLE
            } else {
                see_more_hijo_mayor.visibility = View.GONE
            }
        }

        btn_gestante.setOnClickListener {
            if (see_more_gestante.visibility == View.GONE) {
                see_more_gestante.visibility = View.VISIBLE
            } else {
                see_more_gestante.visibility = View.GONE
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