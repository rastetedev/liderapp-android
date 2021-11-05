package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UpdatePasswordRequest(
    @SerializedName("old_password") var oldPassword: String = "",
    @SerializedName("new_password") var newPassword: String = ""
) : Serializable