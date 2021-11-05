package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateProfileRequest(
    @SerializedName("user_id") val userId: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String ,
    @SerializedName("address") val address: String
) : Serializable