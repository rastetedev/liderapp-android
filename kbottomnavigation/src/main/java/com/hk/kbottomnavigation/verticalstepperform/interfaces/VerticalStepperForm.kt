package com.hk.kbottomnavigation.verticalstepperform.interfaces

import android.view.View

interface VerticalStepperForm {
    fun createStepContentView(stepNumber: Int): View?
    fun onStepOpening(stepNumber: Int)
    fun sendData()
}