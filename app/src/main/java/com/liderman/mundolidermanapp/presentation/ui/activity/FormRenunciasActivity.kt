package com.liderman.mundolidermanapp.presentation.ui.activity

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.getString
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_form_renuncias.*

class FormRenunciasActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_form_renuncias

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Renuncias", R.color.white)
        btn_send_renuncia_form.setOnClickListener {
            sendEmail(
                "gzela@liderman.com.pe", // Para
                "Renuncia de Trabajo", // ASUNTO
                crearMessage()
            )

            showDialog()
        }

    }

    override fun onResume() {
        super.onResume()

    }

    private fun showDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_correcto_renuncias)

        //Los items del pop up
        val btnGoRenuncia: AppCompatButton =
            dialog.findViewById<View>(R.id.btn_continuar_renuncia_form) as AppCompatButton

        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent);
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()

        btnGoRenuncia.setOnClickListener {
            startActivityE(RenunciaActivity::class.java)
            dialog.dismiss()
        }


    }


    fun crearMessage(): String {
        return "Estimada," +
                "\n" +
                "\n" +
                "Yo " + txt_name_user_renuncia.getString() +
                " con DNI " + txt_dni_renuncia.getString() +
                ", teléfono personal " + txt_telefono_user_renuncia.getString() +
                " y correo electrónico " + txt_email_renuncia.getString() +
                " de la unidad " + txt_unidad_actual_renuncia.getString() +
                " con Líder zonal " + txt_lider_zonal_renuncia.getString() +
                " y Jefe de operaciones " + txt_operaciones_renuncia.getString() +
                " deseo presentar a usted mi renuncia con 30 días de anticipación. " +
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


}