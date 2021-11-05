package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.sgidoc.SgiDocEntity
import java.io.Serializable

data class SgiDocResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<SgiDocEntity>?
) : Serializable