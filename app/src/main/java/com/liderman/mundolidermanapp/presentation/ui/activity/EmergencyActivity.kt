package com.liderman.mundolidermanapp.presentation.ui.activity

import android.annotation.SuppressLint
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityDial
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_emergency.*

class EmergencyActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_emergency

    @SuppressLint("SetTextI18n")
    override fun onCreate() {
        super.onCreate()
        setSupportActionBar(resources.getString(R.string.option_líneas_emergencia), R.color.white)
        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"
        btn_0.setOnClickListener {
            startActivityDial("113")
        }
        btn_1.setOnClickListener {
            startActivityDial("105")
        }
        btn_2.setOnClickListener {
            startActivityDial("110")
        }
        btn_3.setOnClickListener {
            startActivityDial("115")
        }
        btn_4.setOnClickListener {
            startActivityDial("116")
        }
        btn_5.setOnClickListener {
            startActivityDial("1818")
        }
        btn_6.setOnClickListener {
            startActivityDial("100")
        }
        btn_7.setOnClickListener {
            startActivityDial("106")
        }
    }
}
