package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.presentation.extensions.isEmpty
import com.liderman.mundolidermanapp.utils.addOnGlobalLayoutListener
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.utils.showError
import kotlinx.android.synthetic.main.activity_contact_tareo.*
import kotlinx.android.synthetic.main.activity_contact_tareo.h

class ContactTareoActivity : LiderManBaseActivity() {
    private var zonaSelected = ""
    private var emailSelected = ""

    private fun getZone() = listOf(
        mapOf("description" to "Lima Norte", "value" to "1"),
        mapOf("description" to "Lima Sur", "value" to "2"),
        mapOf("description" to "Lima Centro", "value" to "3"),
        mapOf("description" to "Lima Oeste", "value" to "4"),
        mapOf("description" to "Resguardo / Guardianía (LIM)", "value" to "5"),
        mapOf("description" to "Disponibles", "value" to "6"),
        mapOf("description" to "Provincia Norte", "value" to "7"),
        mapOf("description" to "Provincia Sur", "value" to "8"),
        mapOf("description" to "Provincia Centro", "value" to "9"),
        mapOf("description" to "Provincia Oriente", "value" to "10"),
        mapOf("description" to "Provincia Minas", "value" to "11"),
        mapOf("description" to "Resguardo / Guardianía (PROV)", "value" to "12")

    )

    private fun emails() = listOf(
        "jvtareonorte@liderman.com.pe",
        "jvtareosur@liderman.com.pe",
        "jvtareocentro@liderman.com.pe",
        "jvtareosur@liderman.com.pe",
        "jvtareosur@liderman.com.pe",
        "Tareo24@liderman.com.pe",
        "jvregionnorte@liderman.com.pe",
        "jvregionsur@liderman.com.pe",
        "jvregioncentro@liderman.com.pe",
        "jvregioncentro@liderman.com.pe",
        "jvregionsur@liderman.com.pe",
        "jvtareosur@liderman.com.pe",
    )

    override fun getView(): Int = R.layout.activity_contact_tareo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar("Tareo", R.color.white)
        typeZone.setAdapter(getZone(), "Zonas")
        typeZone.addOnGlobalLayoutListener {
            typeZone.layoutParams = (typeZone.layoutParams as LinearLayout.LayoutParams).apply {
                height = h.height
            }
        }
        typeZone.setSelection("Zonas")
        typeZone.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (typeZone.selectedDescription.toString().trim().isNotEmpty()) {
                    zonaSelected = typeZone.selectedDescription
                    emailSelected = emails()[typeZone.selectedItemPosition]
                }
            }
        }

        btn_send?.setOnClickListener {
            valid()
        }
    }

    private fun valid() {
        hideAllWrappers()
        if (addComment2.isEmpty()) {
            addComment_wrapper2.showError("Campo requerido")
            return
        }

        if (zonaSelected.isEmpty()) {
            Toast.makeText(this, "Campo requerido : Area", Toast.LENGTH_LONG).show()
            return
        }

        sendEmail(
            emailSelected, // Para
            "Tareo - Reclamo", // ASUNTO
            crearMessage()
        )

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
            //if any thing goes wrong for example no email client application or any exception
            //get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }

    }

    private fun crearMessage(): String {
        return "Estimado," +
                "\n" +
                "\n" +
                "Yo ${PapersManager.loginEntity.names} ${PapersManager.loginEntity.lastNames}" +
                " con DNI " + PapersManager.loginEntity.dni.trim() +
                " con la Zona: ${PapersManager.loginEntity.zona}" +
                " le escribo lo siguiente:" +
                "\n" +
                "\n" +
                addComment2.text.toString() +
                "\n" +
                "\n" + "Saludos cordiales,"
    }
}