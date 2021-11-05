package com.liderman.mundolidermanapp.presentation.ui.activity

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentEntity
import com.liderman.mundolidermanapp.presentation.ui.adapter.MyTicketsAdapter
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import com.liderman.mundolidermanapp.utils.ifIsEmpty
import kotlinx.android.synthetic.main.activity_mis_boletas_detalle.*


class MyTicketsDetailActivity : LiderManBaseActivity() {

    private lateinit var adapter: MyTicketsAdapter

    private lateinit var selectedYearVal: String
    private lateinit var selectedMonthVal: String
    private lateinit var paymentEntity: PaymentEntity

    override fun getView(): Int = R.layout.activity_mis_boletas_detalle

    override fun onCreate() {
        super.onCreate()

        intent.extras?.let {
            paymentEntity = it.get("extra0") as PaymentEntity
            selectedMonthVal = it.get("extra1") as String
            selectedYearVal = it.get("extra2") as String
        }

        setSupportActionBar(
            "Detalle : $selectedMonthVal $selectedYearVal",
            R.color.white
        )

        txt_fullname?.text = paymentEntity.headerEntityPaymenList?.get(0)?.fullName.ifIsEmpty()
        txt_costo_hora?.text =
            "S/. " + paymentEntity.headerEntityPaymenList?.get(0)?.paymentXHour.ifIsEmpty()
        txt_dias_trabajados?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.workDays.ifIsEmpty()
        txt_fecha_fin_vacaciones?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.vacationEndDate.ifIsEmpty()
        txt_fecha_ingreso?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.workStartDate?.substring(0,10).ifIsEmpty()
        txt_fecha_inicio_vacaciones?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.vacationStartDate.ifIsEmpty()
        txt_horas_extra_100?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.extraHours100.ifIsEmpty()
        txt_horas_extra_25?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.extraHours25.ifIsEmpty()
        txt_horas_extra_35?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.extraHours35.ifIsEmpty()
        txt_horas_trabajadas?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.workHours.ifIsEmpty()
        txt_inasistencias?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.noWorkDays.ifIsEmpty()
        txt_sueldo_basico?.text =
            "S/. " + paymentEntity.headerEntityPaymenList?.get(0)?.basicSalary.ifIsEmpty()
        txt_tipo_planilla?.text =
            paymentEntity.headerEntityPaymenList?.get(0)?.tipoPlanilla.ifIsEmpty()

        paymentEntity.detailEntityPaymenList?.let {
            adapter = MyTicketsAdapter()
            rv_detail?.removeAllViews()
            rv_detail?.removeAllViewsInLayout()
            rv_detail?.adapter = adapter
            adapter.data = it
        }

    }


}

