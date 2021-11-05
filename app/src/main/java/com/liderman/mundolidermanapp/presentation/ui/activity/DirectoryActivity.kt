package com.liderman.mundolidermanapp.presentation.ui.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import com.google.firebase.analytics.FirebaseAnalytics
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.entity.contact.DirectoryEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.ui.adapter.DirectoryAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_directory.*
import java.io.Serializable
import javax.inject.Inject

class DirectoryActivity : LiderManBaseActivity(), ContactPresenter.View {
    @Inject
    lateinit var contactPresenter: ContactPresenter

    private val listItemContact: ArrayList<DirectoryEntity> = arrayListOf()
    lateinit var contacts: List<ContactEntity>

    private var numberAreaIdSelected: Int? = null

    private lateinit var adapter: DirectoryAdapter

    override fun getView(): Int = R.layout.activity_directory

    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        setSupportActionBar("Consultas", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)

        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"

        listItemContact.add(DirectoryEntity(1, "Tareo", R.drawable.ic_icono_tareo))
        listItemContact.add(DirectoryEntity(2, "Uniformes", R.drawable.ic_icono_uniformes))
        listItemContact.add(DirectoryEntity(3, "Planillas", R.drawable.ic_icono_planillas))
        listItemContact.add(DirectoryEntity(4, "Sucamec", R.drawable.ic_icono_sucamec))
        listItemContact.add(DirectoryEntity(5, "Bienestar", R.drawable.ic_icono_bienestar_social))
        listItemContact.add(DirectoryEntity(6, "Contratos", R.drawable.ic_icono_contratos))
        listItemContact.add(DirectoryEntity(7, "Vacaciones", R.drawable.ic_icono_vacaciones))
        listItemContact.add(DirectoryEntity(8, "Otros", R.drawable.ic_icono_otros))

        contacts = PapersManager.contactsEntity

        val numberAreaPlanillas1 = contacts.first {
            it.id == 14
        }
        val numberAreaPlanillas2 = contacts.first {
            it.id == 15
        }


        val numberSucamec1 = contacts.first {
            it.id == 16
        }
        val numberSucamec2 = contacts.first {
            it.id == 17
        }
        val numberSucamec3 = contacts.first {
            it.id == 18
        }


        val numberBienestar1 = contacts.first {
            it.id == 19
        }
        val numberBienestar2 = contacts.first {
            it.id == 20
        }
        val numberBienestar3 = contacts.first {
            it.id == 21
        }
        val numberBienestar4 = contacts.first {
            it.id == 22
        }
        val numberBienestar5 = contacts.first {
            it.id == 23
        }


        val numberContrato1 = contacts.first {
            it.id == 24
        }
        val numberContrato2 = contacts.first {
            it.id == 25
        }


        val numberVacacion = contacts.first {
            it.id == 26
        }


        val numberOtros1 = contacts.first {
            it.id == 27
        }
        val numberOtros2 = contacts.first {
            it.id == 28
        }



        adapter = DirectoryAdapter { c ->
            when (c.id) {
                1 -> {
                    //Firebase
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaTareo", bundle)
                    showDialogTareo()
                }

                2 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaUniformes", bundle)
                    showDialogUniformes()
                }

                3 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaPlanillas", bundle)
                    startActivityE(
                        PlanillasActivity::class.java,
                        numberAreaPlanillas1,
                        numberAreaPlanillas2
                    )
                }
                4 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaSucamec", bundle)
                    startActivityE(
                        SucamecActivity::class.java,
                        numberSucamec1,
                        numberSucamec2,
                        numberSucamec3
                    )
                }
                5 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaBienestarSocial", bundle)
                    startActivityE(
                        BienestarSocialActivity::class.java,
                        numberBienestar1,
                        numberBienestar2,
                        numberBienestar3,
                        numberBienestar4,
                        numberBienestar4,
                        numberBienestar5
                    )
                }
                6 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaContrato", bundle)
                    startActivityE(
                        ContratoActivity::class.java,
                        numberContrato1,
                        numberContrato2
                    )
                }

                7 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaVacaciones", bundle)
                    startActivityE(
                        VacacionesActivity::class.java,
                        numberVacacion
                    )
                }
                8 -> {
                    val analytic = FirebaseAnalytics.getInstance(this)
                    val bundle = Bundle()
                    bundle.putString("zona", PapersManager.loginEntity.zona)
                    bundle.putString("fechaIngreso", PapersManager.loginEntity.fechaIngreso)
                    bundle.putString("genero", PapersManager.loginEntity.sexo)
                    bundle.putString("edad", PapersManager.loginEntity.edad)
                    bundle.putString("user", "${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames }")
                    analytic.logEvent("InitConsultaOtros", bundle)
                    startActivityE(
                        OtrosActivity::class.java,
                        numberOtros1,
                        numberOtros2
                    )
                }
            }
        }
        recycler.removeAllViews()
        recycler.removeAllViewsInLayout()
        recycler.adapter = adapter
        adapter.data = listItemContact
    }

    private fun getWhatsapp(contactAreaId: Int) {

        val contactSelected = contacts.firstOrNull {
            it.id == contactAreaId

        }

        numberAreaIdSelected = contactAreaId

        contactSelected?.let {
            if (!it.number.isNullOrEmpty()) {
                startActivityWhatssap(it.number, WHATTSAP)
            } else {
                Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
            }
        } ?: also {
            Toast.makeText(this, "Sin número de contacto", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDialogTareo() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.tareo_dialog)
        //ALL ITEMS IN THE VIEW
        val btnBotonLimaView: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_lima_tareo) as AppCompatButton
        val btnBotonProvinciaView: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_provincia_tareo) as AppCompatButton
        val linerPrincipal: LinearLayoutCompat =
            dialog.findViewById<View>(R.id.lnl_principal) as LinearLayoutCompat
        val linerLimaPrincipal: LinearLayoutCompat =
            dialog.findViewById<View>(R.id.lnl_lima_principal) as LinearLayoutCompat
        val linerProvinciaPrincipal: LinearLayoutCompat =
            dialog.findViewById<View>(R.id.lnl_provinvica_principal) as LinearLayoutCompat

        //Botones de Lima - Lima
        val btnLimaNorte: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_norte_lima) as AppCompatButton
        val btnLimaSur: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_sur_lima) as AppCompatButton
        val btnLimaCentro: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_centro_lima) as AppCompatButton
        val btnLimaOeste: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_oeste_lima) as AppCompatButton
        val btnLimaResgua: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_resgua_lima) as AppCompatButton
        val btnDisponible: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_disponible_tareo) as AppCompatButton

        //Botones de Provincia
        val btnProvinciaNorte: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_norte_provinvica) as AppCompatButton
        val btnProvinciaSur: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_sur_provinvica) as AppCompatButton
        val btnProvinciaCentro: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_centro_provinvica) as AppCompatButton
        val btnProvinciaOeste: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_oeste_provinvica) as AppCompatButton
        val btnProvinciaMinas: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_minas_provinvica) as AppCompatButton
        val btnProvinciaResgua: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_resgua_provinvica) as AppCompatButton


        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnBotonLimaView.setOnClickListener {
            linerPrincipal.visibility = View.GONE
            linerLimaPrincipal.visibility = View.VISIBLE
            linerProvinciaPrincipal.visibility = View.GONE
        }
        btnBotonProvinciaView.setOnClickListener {
            linerPrincipal.visibility = View.GONE
            linerLimaPrincipal.visibility = View.GONE
            linerProvinciaPrincipal.visibility = View.VISIBLE
        }

        //Todo poner los numero que llegan en el servicio
        //TODO LIMA - LIMA
        btnLimaNorte.setOnClickListener {
            getWhatsapp(1)
        }
        btnLimaSur.setOnClickListener {
            getWhatsapp(2)
        }
        btnLimaCentro.setOnClickListener {
            getWhatsapp(3)
        }
        btnLimaOeste.setOnClickListener {
            getWhatsapp(4)
        }
        btnLimaResgua.setOnClickListener {
            getWhatsapp(5)
        }
        btnDisponible.setOnClickListener {
            getWhatsapp(6)
        }

        //TODO PROVINCIA
        btnProvinciaNorte.setOnClickListener {
            getWhatsapp(7)
        }
        btnProvinciaSur.setOnClickListener {
            getWhatsapp(8)
        }
        btnProvinciaCentro.setOnClickListener {
            getWhatsapp(9)
        }
        btnProvinciaOeste.setOnClickListener {
            getWhatsapp(10)
        }
        btnProvinciaMinas.setOnClickListener {
            getWhatsapp(11)
        }
        btnProvinciaResgua.setOnClickListener {
            getWhatsapp(12)
        }
    }

    private fun showDialogUniformes() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_uniformes)

        val btnEntentido: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_entendido) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        val numberAreaUniforme = contacts.first {
            it.id == 13
        }

        btnEntentido.setOnClickListener {
            startActivityE(
                UniformesActivity::class.java,
                numberAreaUniforme
            )
            dialog.dismiss()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP) {
            showBottomSheetDialog { serviceStars, effective ->
                contactPresenter.qualityServices(
                    QualityRequest(
                        PapersManager.loginEntity.userId,
                        numberAreaIdSelected ?: 0,
                        serviceStars,
                        effective
                    )
                )
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }
}

