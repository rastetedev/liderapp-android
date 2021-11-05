package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.amatrata.AmaTratoEntity
import java.io.Serializable

data class AmaTrataResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<AmaTratoEntity>?
) : Serializable