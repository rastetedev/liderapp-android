package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.net.Uri
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
import kotlinx.android.synthetic.main.activity_claim.*
import kotlinx.android.synthetic.main.activity_claim.h

class ClaimActivity : LiderManBaseActivity() {

    private var areaSelected = ""

    override fun getView(): Int = R.layout.activity_claim

    private fun getArea() = listOf(
        mapOf("description" to "Norte", "value" to "1"),
        mapOf("description" to "Sur", "value" to "2"),
        mapOf("description" to "Centro", "value" to "3"),
        mapOf("description" to "Oriente", "value" to "4"),
        mapOf("description" to "Minas", "value" to "5"),
        mapOf("description" to "Resguardos/Guardianias", "value" to "6")
    )


    override fun onCreate() {
        super.onCreate()
        setSupportActionBar("Reclamos", R.color.white)

        activeAllWrappers()
        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"

        typeArea.setAdapter(getArea(), "Elige tu área")
        typeArea.addOnGlobalLayoutListener {

            typeArea.layoutParams = (typeArea.layoutParams as LinearLayout.LayoutParams).apply {
                height = h.height
            }
        }
        typeArea.setSelection("Elige tu área")
        typeArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (typeArea.selectedDescription.toString().trim().isNotEmpty()) {
                    areaSelected = typeArea.selectedDescription
                }
            }
        }
        zona_reclamo?.setText(PapersManager.loginEntity.zona)
        btn_send.setOnClickListener {
            valid()
        }
    }

    private fun valid() {
        hideAllWrappers()
        if (addComment2.isEmpty()) {
            addComment_wrapper2.showError("Campo requerido")
            return
        }

        if (areaSelected.isEmpty()) {
            Toast.makeText(this, "Campo requerido : Area", Toast.LENGTH_LONG).show()
            return
        }

        sendEmail(
            "frivas@liderman.com.pe", // Para
            "Reclamo - Perfil", // ASUNTO
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
                addComment2.text.toString() +
                "\n" +
                "\n" + "Saludos cordiales,"
    }


}