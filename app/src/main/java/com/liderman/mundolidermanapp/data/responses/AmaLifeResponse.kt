package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.amalife.AmaLifeEntity
import java.io.Serializable

data class AmaLifeResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<AmaLifeEntity>?
) : Serializable