package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.login.LoginEntity
import java.io.Serializable

data class LoginResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: LoginEntity?
) : Serializable