package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName

data class TrafficRequest(
    @SerializedName("user_dni") var dni: String = ""
)