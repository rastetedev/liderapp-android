package com.liderman.mundolidermanapp.presentation.ui.activity


import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Toast
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import com.liderman.mundolidermanapp.utils.addOnGlobalLayoutListener
import com.liderman.mundolidermanapp.presentation.extensions.startActivityE
import com.liderman.mundolidermanapp.presentation.presenter.UtilidadPresenter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_mis_utilidades.*
import java.io.Serializable
import java.util.*
import java.util.Calendar.YEAR
import javax.inject.Inject

class UtilidadActivity : LiderManBaseActivity(), UtilidadPresenter.View {

    @Inject
    lateinit var utilidadPresenter: UtilidadPresenter

    private var selectedYearVal = ""
    private var selectedMonthVal = ""
    private var selectedMonthDescription = ""
    private var selectedYearDescription = ""

    private fun getMonths() = listOf(
        mapOf("description" to "Enero", "value" to "01"),
        mapOf("description" to "Febrero", "value" to "02"),
        mapOf("description" to "Marzo", "value" to "03"),
        mapOf("description" to "Abril", "value" to "04"),
        mapOf("description" to "Mayo", "value" to "05"),
        mapOf("description" to "Junio", "value" to "06"),
        mapOf("description" to "Julio", "value" to "07"),
        mapOf("description" to "Agosto", "value" to "08"),
        mapOf("description" to "Setiembre", "value" to "09"),
        mapOf("description" to "Octubre", "value" to "10"),
        mapOf("description" to "Noviembre", "value" to "11"),
        mapOf("description" to "Diciembre", "value" to "12")
    )

    private fun getYears(year: Int): List<Map<String, String>> {

        val years = mutableListOf<Map<String, String>>()

        for (i in year downTo (year-20)) {
            years.add(
                mapOf("description" to "$i", "value" to "$i")
            )
        }
        return years
    }

    override fun getView(): Int = R.layout.activity_mis_utilidades

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
        setSupportActionBar("Mis Utiliadades", R.color.white)

        val year = Calendar.getInstance().get(YEAR)

        activeAllWrappers()
        val months = getMonths()
        val years = getYears(year)

        Log.d("YEARS", years.size.toString())

        paymentMonth?.setAdapter(months, "Elige el mes")
        paymentYear?.setAdapter(years, "Elige el año")

        paymentMonth.addOnGlobalLayoutListener {

            paymentMonth.layoutParams =
                (paymentMonth.layoutParams as LinearLayout.LayoutParams).apply {
                    height = h.height
                }

            paymentYear.layoutParams =
                (paymentYear.layoutParams as LinearLayout.LayoutParams).apply {
                    height = h.height
                }
        }


        paymentMonth.setSelection("Elige el mes")
        paymentMonth.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (paymentMonth.selectedDescription.toString().trim().isNotEmpty()) {
                    selectedMonthVal = paymentMonth.selectedItem["value"].toString()
                    selectedMonthDescription = paymentMonth.selectedDescription

                }
            }
        }

        paymentYear.setSelection("Elige el año")
        paymentYear.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, i: Int, id: Long) {
                if (paymentYear.selectedDescription.toString().trim().isNotEmpty()) {
                    selectedYearVal = paymentYear.selectedItem["value"].toString()
                    selectedYearDescription = paymentYear.selectedDescription
                }
            }
        }

        btn_search?.setOnClickListener {
            if (selectedMonthVal.isNotEmpty() && selectedYearVal.isNotEmpty()) {
                utilidadPresenter.getUtilidades("${selectedYearVal}${selectedMonthVal}")
            } else {
                Toast.makeText(this, "Selecciona el mes y el año", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onResume() {
        super.onResume()
        utilidadPresenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        utilidadPresenter.detachView()
    }

    override fun successUtilidad(utilidadEntity: UtilidadEntity) {
        startActivityE(
            UtilidadDetailActivity::class.java,
            utilidadEntity as Serializable,
            selectedMonthDescription,
            selectedYearDescription
        )
    }


}