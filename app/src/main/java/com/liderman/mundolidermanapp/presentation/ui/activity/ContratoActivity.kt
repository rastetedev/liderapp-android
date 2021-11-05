package com.liderman.mundolidermanapp.presentation.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import com.liderman.mundolidermanapp.BuildConfig

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import com.liderman.mundolidermanapp.data.request.QualityRequest
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.startActivityWhatssap
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrlForResult
import com.liderman.mundolidermanapp.presentation.presenter.ContactPresenter
import com.liderman.mundolidermanapp.presentation.presenter.ContractPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_contrato.*
import java.io.Serializable
import java.util.*
import javax.inject.Inject

class ContratoActivity : LiderManBaseActivity(), ContractPresenter.View, ContactPresenter.View {

    @Inject
    lateinit var contactPresenter: ContactPresenter

    @Inject
    lateinit var contractPresenter: ContractPresenter

    private var contractEntity: ContractEntity? = null

    private var dialog: Dialog? = null

    private lateinit var lnlOptions: LinearLayoutCompat
    private lateinit var lnlSuccess: LinearLayoutCompat
    private lateinit var lnlDownload: LinearLayoutCompat

    private var numberArea1: ContactEntity? = null
    private var numberArea2: ContactEntity? = null

    private var numberSelected: ContactEntity? = null

    val PERMISSION_WRITE_STORAGE = 5999


    override fun getView(): Int = R.layout.activity_contrato

    @Suppress("UNCHECKED_CAST")
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Contratos", R.color.white)
        component.inject(this)
        contactPresenter.attachView(this)
        contractPresenter.attachView(this)

        numberArea1 = intent.getSerializableExtra("extra0") as? ContactEntity
        numberArea2 = intent.getSerializableExtra("extra1") as? ContactEntity

        contractPresenter.getContract()


        btn_chat.setOnClickListener {
            showDialogWsp()
        }

        btn_contract.setOnClickListener {
            if (txt_dni_user.visibility == View.GONE) {
                txt_dni_user.visibility = View.VISIBLE
            } else {
                txt_dni_user.visibility = View.GONE
            }
        }

        txt_dni_user.setOnClickListener {

            contractEntity?.let {
                startActivityUrlForResult("${BuildConfig.URL_BASE}/${it.url}", CONTRACT)
            } ?: also {
                Toast.makeText(this, "No hay contrato por el momento", Toast.LENGTH_LONG).show()
            }

        }

        btn_constancia_trabajo.setOnClickListener {
            startActivityE(ConstanciaTrabajoActivity::class.java)
        }

        txt_dni_user.text =
            "DNI ${PapersManager.loginEntity.dni.trim()} - ${PapersManager.loginEntity.names}"
    }


    private fun showDialogWsp() {

        dialog = Dialog(this).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.contrato_dialog)
            //ALL ITEMS IN THE VIEW
            val btnBotonLimaView: AppCompatButton =
                findViewById<View>(R.id.btn_lima_contrato) as AppCompatButton
            val btnBotonProvinciaView: AppCompatButton =
                findViewById<View>(R.id.btn_provincia_contrato) as AppCompatButton

            window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            window?.setBackgroundDrawableResource(android.R.color.transparent);
            window?.attributes?.windowAnimations = R.style.AppTheme_Slide
            show()


            btnBotonLimaView.setOnClickListener {
                numberArea1?.let {
                    if (!it.number.isNullOrEmpty()) {
                        numberSelected = it
                        startActivityWhatssap(it.number, WHATTSAP)
                        dismiss()
                    } else {
                        Toast.makeText(
                            this@ContratoActivity,
                            "Sin número de contacto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } ?: also {
                    Toast.makeText(
                        this@ContratoActivity,
                        "Sin número de contacto",
                        Toast.LENGTH_SHORT
                    ).show()
                }


            }
            btnBotonProvinciaView.setOnClickListener {
                numberArea2?.let {
                    if (!it.number.isNullOrEmpty()) {
                        numberSelected = it
                        startActivityWhatssap(it.number, WHATTSAP)
                        dismiss()
                    } else {
                        Toast.makeText(
                            this@ContratoActivity,
                            "Sin número de contacto",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } ?: also {
                    Toast.makeText(
                        this@ContratoActivity,
                        "Sin número de contacto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == WHATTSAP ) {
            numberSelected?.let {
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

        } else if (requestCode == CONTRACT) {
            showDialog()
        }


        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_contract_options)

        lnlOptions =
            dialog.findViewById<View>(R.id.lnl_options) as LinearLayoutCompat
        lnlSuccess =
            dialog.findViewById<View>(R.id.lnl_success) as LinearLayoutCompat
        lnlDownload =
            dialog.findViewById<View>(R.id.lnl_download) as LinearLayoutCompat

        val btnSubscription: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_subscription) as AppCompatButton
        val btnDownload: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_download) as AppCompatButton
        val btnRead: AppCompatButton = dialog.findViewById<View>(R.id.btn_read) as AppCompatButton
        val btnOk: AppCompatButton = dialog.findViewById<View>(R.id.btn_ok) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnSubscription.setOnClickListener {
            contractEntity?.let {
                if (it.state != 2) {
                    contractPresenter.updateContractState(it.id, 1)
                } else {
                    Toast.makeText(
                        this,
                        "Su contrato ya fue suscrito. Si desea puede descargarlo",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        }

        btnDownload.setOnClickListener {
            getDownloadPermission()
        }

        btnOk.setOnClickListener {
            dialog.dismiss()
        }

        btnRead.setOnClickListener {
            contractEntity?.let {
                startActivityUrlForResult("${BuildConfig.URL_BASE}/${it.url}", CONTRACT)
            }
            dialog.dismiss()
        }
    }

    private fun downloadContract(contractEntity: ContractEntity) {
        val request =
            DownloadManager.Request(Uri.parse("${BuildConfig.URL_BASE}/${contractEntity.url}"))
        val title = "Contrato ${PapersManager.loginEntity.dni} - ${Calendar.getInstance().time}"
        request.setTitle(title)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$title.pdf")
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        request.setMimeType("application/pdf")
        request.allowScanningByMediaScanner()
        downloadManager.enqueue(request)
    }

    override fun successContactPresenter(status: Int, vararg args: Serializable) {
        //
    }

    override fun successContract(contractEntity: ContractEntity) {
        this.contractEntity = contractEntity
    }

    override fun successUpdate(contractEntity: ContractEntity) {
        this.contractEntity = contractEntity

        if (contractEntity.state == 1) {
            dialog?.let {
                lnlOptions.visibility = View.GONE
                lnlSuccess.visibility = View.VISIBLE
                lnlDownload.visibility = View.GONE
            }


        } else if (contractEntity.state == 2) {
            dialog?.let {
                lnlOptions.visibility = View.GONE
                lnlSuccess.visibility = View.GONE
                lnlDownload.visibility = View.VISIBLE
            }

        }
    }

    private fun getDownloadPermission() {
        val permissionArrays = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            download()
        } else {
            requestPermissions(permissionArrays, PERMISSION_WRITE_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_WRITE_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    download()
                }
            }

        }
    }

    private fun download() {
        contractEntity?.let {
            if (it.state >= 1) {
                contractPresenter.updateContractState(it.id, 2)
                downloadContract(it)
            } else {
                Toast.makeText(this, "Debe suscribir primero el contrato", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
}