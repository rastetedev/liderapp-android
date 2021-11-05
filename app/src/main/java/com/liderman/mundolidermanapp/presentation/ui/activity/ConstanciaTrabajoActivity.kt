package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.getString
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_constancia_trabajo.*

class ConstanciaTrabajoActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_constancia_trabajo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Contratos", R.color.white)

        btn_send_constancia_trabajo.setOnClickListener {
            if (validate()) {
                sendEmail(
                    "gzela@liderman.com.pe", // Para
                    "Constancia de Trabajo", // ASUNTO
                    crearMessage()
                )
            } else {
                Toast.makeText(
                    this,
                    "Todos los campos a excepción del correo son obligatorios",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    fun crearMessage(): String {
        return "Estimada," +
                "\n" +
                "\n" +
                "Yo " + txt_name_user.getString() +
                " con DNI " + txt_dni_const.getString() +
                ", teléfono personal " + txt_telefono_user.getString() +
                " y correo electrónico " + txt_email.getString() +
                " de la unidad " + txt_unidad_actual.getString() +
                " con Líder zonal " + txt_lider_zonal.getString() +
                " y Jefe de operaciones " + txt_jefe_operaciones.getString() +
                " deseo solicitar a usted una constancia de trabajo con motivo de " + txt_motivo.getString() +
                "\n" +
                "\n Agradeciendo su atención y a la espera de su confirmación, me despido. \n" +
                "\n" +
                "\n" + "Saludos cordiales,"
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

    private fun validate(): Boolean =
        (!txt_name_user?.text.isNullOrEmpty() &&
                !txt_dni_const?.text.isNullOrEmpty() &&
                !txt_telefono_user?.text.isNullOrEmpty() &&
                !txt_lider_zonal?.text.isNullOrEmpty() &&
                !txt_jefe_operaciones?.text.isNullOrEmpty() &&
                !txt_unidad_actual?.text.isNullOrEmpty() &&
                !txt_email?.text.isNullOrEmpty() &&
                !txt_motivo?.text.isNullOrEmpty())


}