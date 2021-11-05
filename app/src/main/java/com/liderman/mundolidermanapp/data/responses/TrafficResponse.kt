package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.traffic.TrafficEntity
import java.io.Serializable

data class TrafficResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: TrafficEntity?
) : Serializable