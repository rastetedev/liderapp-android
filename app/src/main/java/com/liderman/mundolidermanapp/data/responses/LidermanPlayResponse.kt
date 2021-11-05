package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.lidermanplay.LidermanPlayEntity
import java.io.Serializable

data class LidermanPlayResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<LidermanPlayEntity>?
) : Serializable