package com.liderman.mundolidermanapp.presentation.ui.activity

import android.os.Bundle
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityUrl
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_share_friends.*

class ShareFriendsActivity : LiderManBaseActivity() {

    override fun getView(): Int = R.layout.activity_share_friends

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar("Recomienda un amigo", R.color.white)
        lider_boy.text = "Hola, ${PapersManager.loginEntity.names.split(" ")[0]}"
        animationFacebook.setOnClickListener {
            startActivityUrl("https://www.facebook.com/LidermanOficial/")
        }
        animationGmail.setOnClickListener {
            startActivityUrl("https://forms.gle/UVeAd5nDFVF1t5Zr8");
        }

    }


}