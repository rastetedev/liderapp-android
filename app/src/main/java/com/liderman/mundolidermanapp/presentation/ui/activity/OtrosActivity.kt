package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_otros.*
import java.io.Serializable
import javax.inject.Inject

class OtrosActivity : LiderManBaseActivity(), ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    private var numberArea1: ContactEntity? = null
    private var numberArea2: ContactEntity? = null

    override fun getView(): Int = R.layout.activity_otros

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Otros", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity
        numberArea2 = intent.getSerializableExtra("extra1") as? ContactEntity


        btn_training?.setOnClickListener {
            numberArea1?.let { contact ->
                if (!contact.number.isNullOrEmpty()) {
                    startActivityWhatssap(contact.number, WHATTSAP)
                } else {
                    Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
                }
            } ?: also {
                Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
            }
        }

        btn_sgi.setOnClickListener { startActivityE(SigSsomaActivity::class.java) }

        btn_ceses.setOnClickListener {
            startActivityE(
                RenunciaActivity::class.java,
                numberArea2 as Serializable
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP) {

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

}