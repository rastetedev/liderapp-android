package com.liderman.mundolidermanapp.domain.entity.utilidad

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UtilidadEntity(
    @SerializedName("CompaniaSocio") val companiaSocio: String,
    @SerializedName("DiasAnual") val diasAnual: String,
    @SerializedName("DiasBase") val diasBase: String,
    @SerializedName("DiasOrdinarios") val diasOrdinarios: String,
    @SerializedName("Empleado") val empleado: String,
    @SerializedName("EstadoEmpleado") val estadoEmpleado: String,
    @SerializedName("FactorDias") val factorDias: String,
    @SerializedName("FactorSueldo") val factorSueldo: String,
    @SerializedName("FechaFin") val fechaFin: String,
    @SerializedName("FechaIngreso") val fechaIngreso: String,
    @SerializedName("FechaInicio") val fechaInicio: String,
    @SerializedName("FechadePago") val fechaDePago: String,
    @SerializedName("HorasExtras") val horasExtras: String,
    @SerializedName("HorasExtrasenDias") val horasExtrasEnDia: String,
    @SerializedName("MontoAntesImpuesto") val montoAntesImpuesto: String,
    @SerializedName("MontoaRepartir") val montoARepartir: String,
    @SerializedName("NombreCompleto") val nombreCompleto: String,
    @SerializedName("Periodo") val periodo: String,
    @SerializedName("PorcentajeDist") val porcentajeDist: String,
    @SerializedName("PorcentajeRepartir") val procentajeRepartir: String,
    @SerializedName("Sueldo") val sueldo: String,
    @SerializedName("SueldoAnual") val sueldoAnual: String,
    @SerializedName("TipoPlanilla") val tipoPlanilla: String,
    @SerializedName("TotalUtilidad") val totalUtilidad: String,
    @SerializedName("TotalUtilidades") val totalUtilidades: String,
    @SerializedName("UtilidadDias") val utilidadDias: String,
    @SerializedName("UtilidadSueldo") val utilidadSueldo: String
) : Serializable