package com.liderman.mundolidermanapp.domain.entity.payment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaymentDetailEntity(
    @SerializedName("Aportaciones") val aportaciones : String,
    @SerializedName("Descuentos") val descuentos : String,
    @SerializedName("Haberes") val haberes : String,
    @SerializedName("HorasAportaciones") val horasAportaciones : String?,
    @SerializedName("HorasDescuentos") val horasDescuentos : String?,
    @SerializedName("HorasHaberes") val horasHaberes : String?,
    @SerializedName("MontoAportaciones") val montoAportaciones : String?,
    @SerializedName("MontoDescuentos") val montoDescuentos : String?,
    @SerializedName("MontoHaberes") val montoHaberes : String?
) : Serializable