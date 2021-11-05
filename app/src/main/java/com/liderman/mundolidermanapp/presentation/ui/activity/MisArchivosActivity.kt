package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.addOnGlobalLayoutListener
import com.liderman.mundolidermanapp.utils.showError
import com.liderman.mundolidermanapp.presentation.extensions.getString
import com.liderman.mundolidermanapp.presentation.extensions.isEmpty
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_acciones.*
import kotlinx.android.synthetic.main.activity_acciones.addComment2
import kotlinx.android.synthetic.main.activity_acciones.addComment_wrapper2
import kotlinx.android.synthetic.main.activity_acciones.h
import kotlinx.android.synthetic.main.activity_acciones.lider_boy

class MisArchivosActivity : LiderManBaseActivity() {

    private lateinit var actionDescription : String

    override fun getView(): Int = R.layout.activity_acciones

    private fun getAcciones() = listOf(
        mapOf("description" to "Solicitar documento", "value" to "1"),
        mapOf("description" to "Ingresar documento", "value" to "2")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Listado de Acciones", R.color.white)

        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"
        typeAcciones.setAdapter(getAcciones(), "Elige una opción")
        typeAcciones.addOnGlobalLayoutListener {
            typeAcciones.layoutParams =
                (typeAcciones.layoutParams as LinearLayout.LayoutParams).apply {
                    height = h.height
                }
        }
        typeAcciones.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (typeAcciones.selectedDescription.toString().trim().isNotEmpty()) {
                   actionDescription = typeAcciones.selectedDescription.toString().trim()
                }
            }
        }

        btn_send_accion.setOnClickListener { valid() }

    }


    private fun valid() {
        hideAllWrappers()
        if (addComment2.isEmpty()) {
            addComment_wrapper2.showError("Campo requerido")
            return
        }

        sendEmail(
            "jefedearchivos@liderman.com.pe", // Para
            "Solicitud de Documento", // ASUNTO
            crearMessage()
        )

    }


    fun crearMessage(): String {
        return "Estimado," +
                "\n" +
                "\n" +
                "Yo ${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames}" +
                " con DNI " + PapersManager.loginEntity.dni.trim() +
                " le escribo para " + actionDescription +
                "\n" +
                "\n" +
                addComment2.getString() +
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
            startActivity(Intent.createChooser(mIntent, "Escoja su Aplicación de Correo..."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

}