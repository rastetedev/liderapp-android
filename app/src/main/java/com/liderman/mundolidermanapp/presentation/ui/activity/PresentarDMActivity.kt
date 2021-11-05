package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.getString
import com.liderman.mundolidermanapp.presentation.ui.base.UpdateBaseActivity
import kotlinx.android.synthetic.main.activity_presentar_d_m.*

class PresentarDMActivity : UpdateBaseActivity() {

    override fun getView(): Int = R.layout.activity_presentar_d_m


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Bienestar Social", R.color.white)

        rdt_essalud.isChecked = true
        lnl_descansos_medicos.visibility = View.VISIBLE
        lnl_particular.visibility = View.GONE


        rdt_essalud.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                lnl_descansos_medicos.visibility = View.VISIBLE
                lnl_particular.visibility = View.GONE
                rdt_particular.isChecked = false
            }
        }
        rdt_particular.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                lnl_descansos_medicos.visibility = View.VISIBLE
                lnl_particular.visibility = View.VISIBLE
                rdt_essalud.isChecked = false
            }
        }

        // CLICK EN LAS IMAGENES PARA ADJUNTARLAS


        btn_send_dm.setOnClickListener {
            sendEmail(
                "zgalvan@liderman.com.pe", // Para
                "Descanso Médico", // ASUNTO
                crearMessage()
            )
        }


    }

    fun crearMessage(): String {
        val capturaAtentcion = if (rdt_essalud.isChecked) "Essalud" else "Particular"
        return "Estimada," +
                "\n" +
                "\n" +
                "Yo " + PapersManager.loginEntity.names + " con DNI " + txt_dni.getString() +
                " y teléfono " + txt_telefono.getString() +
                " quiero presentarle a usted el descanso médico que me fue otorgado tras mi atención en " + capturaAtentcion +
                ". Así mismo, adjunto los otros documentos requeridos.\n" +
                "\n" +
                "\n A la espera de su confirmación, me despido. \n" +
                "\n" +
                "\n" + "Saludos cordiales"
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentCamera()
                }
            }
            PERMISSION_GALLERY -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    intentGallery()
                }
            }
            else -> {
            }
        }
    }


    private fun sendEmail(recipient: String, subject: String, message: String) {
        val mIntent = Intent(Intent.ACTION_SEND)
        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"
        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            //start email intent
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        } catch (e: Exception) {
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

}