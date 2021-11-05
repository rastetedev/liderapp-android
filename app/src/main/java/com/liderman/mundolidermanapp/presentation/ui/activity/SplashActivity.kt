package com.liderman.mundolidermanapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.utils.PapersManager
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            if (PapersManager.session) startActivityE(MainActivity::class.java) else startActivityE(
                LoginActivity::class.java
            )
        }, 1500)

    }
}
