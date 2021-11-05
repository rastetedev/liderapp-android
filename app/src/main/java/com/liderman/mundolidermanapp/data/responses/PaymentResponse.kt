package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.payment.PaymentEntity
import java.io.Serializable

data class PaymentResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: PaymentEntity?
) : Serializable