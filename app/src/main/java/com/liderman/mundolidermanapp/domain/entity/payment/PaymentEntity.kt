package com.liderman.mundolidermanapp.domain.entity.payment

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PaymentEntity(
    @SerializedName("cabecera") val headerEntityPaymenList : List<PaymentHeaderEntity>?,
    @SerializedName("detalle") val detailEntityPaymenList : List<PaymentDetailEntity>?
) : Serializable