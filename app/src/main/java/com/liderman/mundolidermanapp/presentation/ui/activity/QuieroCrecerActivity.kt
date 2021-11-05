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
import com.liderman.mundolidermanapp.presentation.extensions.isEmpty
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_quiero_crecer.*
import kotlinx.android.synthetic.main.activity_quiero_crecer.h

class QuieroCrecerActivity : LiderManBaseActivity() {

    private var areaSelected = ""

    override fun getView(): Int = R.layout.activity_quiero_crecer

    private fun getArea() = listOf(
        mapOf("description" to "Norte", "value" to "1"),
        mapOf("description" to "Sur", "value" to "2"),
        mapOf("description" to "Centro", "value" to "3"),
        mapOf("description" to "Oriente", "value" to "4"),
        mapOf("description" to "Minas", "value" to "5"),
        mapOf("description" to "Resguardos/Guardianias", "value" to "6")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Ama Crece", R.color.white)

        activeAllWrappers()
        typeAreaAmaCrece.setAdapter(getArea(), "Elige tu área")

        typeAreaAmaCrece.addOnGlobalLayoutListener {

            typeAreaAmaCrece.layoutParams =
                (typeAreaAmaCrece.layoutParams as LinearLayout.LayoutParams).apply {
                    height = h.height
                }
        }

        zona_amacrece?.setText(PapersManager.loginEntity.zona)


        typeAreaAmaCrece.setSelection("Elige tu área")
        typeAreaAmaCrece.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (typeAreaAmaCrece.selectedDescription.toString().trim().isNotEmpty()) {
                    areaSelected = typeAreaAmaCrece.selectedDescription
                }
            }
        }


        btn_send_amacrece.setOnClickListener { valid() }

    }

    private fun valid() {
        hideAllWrappers()
        if (addComment2_amacrece.isEmpty()) {
            addComment_wrapper2_amacrece.showError("Campo requerido")
            return
        }

        if (areaSelected.isEmpty()) {
            Toast.makeText(this, "Campo requerido : Area", Toast.LENGTH_LONG).show()
            return
        }

        sendEmail(
            "csanchez@liderman.com.pe", // Para
            "Quiero crecer", // ASUNTO
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
                " con la Zona: ${PapersManager.loginEntity.zona} - Area: $areaSelected" +
                " le escribo lo siguiente:" +
                "\n" +
                "\n" +
                addComment2_amacrece.text.toString() +
                "\n" +
                "\n" + "Saludos cordiales,"
    }


}