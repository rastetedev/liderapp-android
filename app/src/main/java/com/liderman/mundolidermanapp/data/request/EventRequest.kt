package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class EventRequest (
    @SerializedName("evento") var event : String,
    @SerializedName("zona") var zona : String,
    @SerializedName("genero") var genero : String ,
    @SerializedName("edad") var edad : String,
    @SerializedName("fechaIngreso") var fechaIngreso: String,
): Serializable