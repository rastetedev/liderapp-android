package com.liderman.mundolidermanapp.presentation.ui.activity

import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import com.liderman.mundolidermanapp.presentation.ui.base.LiderManBaseActivity
import kotlinx.android.synthetic.main.activity_mis_utilidades_detalle.*


class UtilidadDetailActivity : LiderManBaseActivity() {

    private lateinit var selectedYearVal: String
    private lateinit var selectedMonthVal: String
    private lateinit var utilidadEntity: UtilidadEntity

    override fun getView(): Int = R.layout.activity_mis_utilidades_detalle

    override fun onCreate() {
        super.onCreate()

        intent.extras?.let {
            utilidadEntity = it.get("extra0") as UtilidadEntity
            selectedMonthVal = it.get("extra1") as String
            selectedYearVal = it.get("extra2") as String
        }

        setSupportActionBar(
            "Utilidad : $selectedMonthVal $selectedYearVal",
            R.color.white
        )

        with(utilidadEntity) {

            txt_compania_socio.text = this.companiaSocio.ifIsEmpty()
            txt_dias_anual.text = this.diasAnual.ifIsEmpty()
            txt_dias_base.text = this.diasBase.ifIsEmpty()
            txt_dias_ordinarios.text = this.diasOrdinarios.ifIsEmpty()
            txt_empleado.text = this.empleado.ifIsEmpty()
            txt_factor_dias.text = this.factorDias.ifIsEmpty()
            txt_factor_sueldo.text = this.factorSueldo.ifIsEmpty()
            txt_fecha_fin.text = this.fechaFin.ifIsEmpty()
            txt_fecha_ingreso.text = this.fechaIngreso.ifIsEmpty()
            txt_fecha_inicio.text = this.fechaInicio.ifIsEmpty()
            txt_fecha_pago.text = this.fechaDePago.ifIsEmpty()
            txt_horas_extras.text = this.horasExtras.ifIsEmpty()
            txt_horas_extras_dias.text = this.horasExtrasEnDia.ifIsEmpty()
            txt_monto_antes_impuesto.text = this.montoAntesImpuesto.ifIsEmpty()
            txt_monto_a_repartir.text = this.montoARepartir.ifIsEmpty()
            txt_nombre_completo.text = this.nombreCompleto.ifIsEmpty()
            txt_periodo.text = this.periodo.ifIsEmpty()
            txt_porcentaje_dist.text = this.porcentajeDist.ifIsEmpty()
            txt_porcentaje_repartir.text = this.procentajeRepartir.ifIsEmpty()
            txt_sueldo.text = this.sueldo.ifIsEmpty()
            txt_sueldo_anual.text = this.sueldoAnual.ifIsEmpty()
            txt_tipo_planilla.text = this.tipoPlanilla.ifIsEmpty()
            txt_total_utilidad.text = this.totalUtilidad.ifIsEmpty()
            txt_utilidad_dias.text = this.utilidadDias.ifIsEmpty()
            txt_utilidad_sueldo.text = this.utilidadSueldo.ifIsEmpty()
            
        }

    }


}

fun String?.ifIsEmpty(): String {
    return if (this.isNullOrEmpty()) "-" else this
}