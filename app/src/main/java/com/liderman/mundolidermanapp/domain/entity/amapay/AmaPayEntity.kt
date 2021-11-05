package com.liderman.mundolidermanapp.domain.entity.amapay


import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

data class AmaPayEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("dateFormatted") val date: Date,
    @SerializedName("event") val event: String
) : Serializable {

    fun getCalendarDate(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return calendar
    }



}