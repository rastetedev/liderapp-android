package com.liderman.mundolidermanapp.domain.entity.payment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaymentHeaderEntity(
    @SerializedName("ApellidosNombres") val fullName : String,
    @SerializedName("Codigo") val code : String,
    @SerializedName("Compania") val company : String,
    @SerializedName("CostoHora") val paymentXHour : String,
    @SerializedName("DiasTrabajados") val workDays : String,
    @SerializedName("FechaIngreso") val workStartDate : String,
    @SerializedName("FechaInicioVacaciones") val vacationStartDate : String,
    @SerializedName("FechaFinVacaciones") val vacationEndDate : String,
    @SerializedName("HorasExtras100") val extraHours100 : String,
    @SerializedName("HorasExtras25") val extraHours25 : String,
    @SerializedName("HorasExtras35") val extraHours35 : String,
    @SerializedName("HorasTrabajadas") val workHours : String,
    @SerializedName("Inasistencias") val noWorkDays : String,
    @SerializedName("Periodo") val period : String,
    @SerializedName("SistemaPension") val financialCompany : String,
    @SerializedName("SueldoBasico") val basicSalary : String,
    @SerializedName("TipoPlanilla") val tipoPlanilla : String
) : Serializable