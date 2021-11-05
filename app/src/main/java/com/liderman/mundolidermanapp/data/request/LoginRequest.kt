package com.liderman.mundolidermanapp.data.request

import java.io.Serializable

data class LoginRequest(
    val dni: String ,
    val password: String
) : Serializable