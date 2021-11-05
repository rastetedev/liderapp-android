package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LogoutRequest(
    @SerializedName("user_id")
    val userId: String ,
    val token: String
) : Serializable