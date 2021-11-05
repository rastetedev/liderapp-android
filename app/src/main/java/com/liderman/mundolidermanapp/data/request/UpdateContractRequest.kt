package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdateContractRequest(
    @SerializedName("id") val id : Int,
    @SerializedName("user_id") val userId: String,
    @SerializedName("token") val token: String,
    @SerializedName("state") val state: Int
) : Serializable