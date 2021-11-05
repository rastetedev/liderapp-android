package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: Profile?
) : Serializable {

    data class Profile(
        @SerializedName("photo_url") val photoUrl : String,
        @SerializedName("email") val email : String,
        @SerializedName("phone") val phone : String,
        @SerializedName("address") val address : String,
        @SerializedName("number_badges") val numberBadges : String,
        @SerializedName("points") val points : String,
        @SerializedName("names") val names : String,
        @SerializedName("last_names") val lastNames : String,
        @SerializedName("position") val position : String,
        @SerializedName("client") val client : String
    )

}