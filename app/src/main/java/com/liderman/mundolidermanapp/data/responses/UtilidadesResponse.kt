package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.utilidad.UtilidadEntity
import java.io.Serializable

data class UtilidadesResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<UtilidadEntity>?
) : Serializable