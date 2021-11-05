package com.liderman.mundolidermanapp.domain.entity.task

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class TaskEntity(
    @SerializedName("Cargo") val position: String,
    @SerializedName("Cliente") val client: String,
    @SerializedName("Dia") val day: String,
    @SerializedName("Evento") val event: String,
    @SerializedName("FechaTareo") val completeDate: String,
    @SerializedName("Mes") val month: String,
    @SerializedName("MesNombre") val monthName: String
) {

    fun toDate(): Date {
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        try {
            return format.parse(completeDate)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return Date()
    }

    fun color(): Int {
        return when (this.event) {
            "D" -> R.color.d_value
            "T" -> R.color.t_value
            "N" -> R.color.n_value
            "G" -> R.color.g_value
            "F" -> R.color.f_value
            "FF" -> R.color.ff_value
            "ASERV" -> R.color.aserv_value
            "BAJA" -> R.color.baja_value
            "DESC" -> R.color.desc_value
            "DM" -> R.color.dm_value
            "X" -> R.color.x_value
            "SUBEN" -> R.color.suben_value
            "SUBMA" -> R.color.subma_value
            "VC" -> R.color.vc_value
            "VT" -> R.color.vt_value
            "S" -> R.color.s_value
            "PT" -> R.color.pt_value
            "IP" -> R.color.ip_value
            "ITTSS" -> R.color.ittss_value
            "LCGOC" -> R.color.lcgoc_value
            "LSGOC" -> R.color.lsgoc_value
            "PN" -> R.color.pn_value
            "PP" -> R.color.pp_value
            "SF" -> R.color.sf_value
            "SP" -> R.color.sp_value
            "SNCUB" -> R.color.sncub_value
            "SDESA" -> R.color.sdesa_value
            else -> R.color.d_value
        }
    }

}