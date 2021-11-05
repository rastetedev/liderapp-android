package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.amapay.AmaPayEntity
import java.io.Serializable

data class AmaPayResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<AmaPayEntity>?
) : Serializable