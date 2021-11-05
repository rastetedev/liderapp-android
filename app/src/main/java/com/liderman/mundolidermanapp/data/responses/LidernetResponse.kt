package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import java.io.Serializable

data class LidernetResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: LiderNetEntity?
) : Serializable