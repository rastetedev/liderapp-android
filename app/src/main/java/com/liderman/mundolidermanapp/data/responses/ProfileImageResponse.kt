package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProfileImageResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: ProfileImage?
) : Serializable {

    data class ProfileImage(
        @SerializedName("image_url_path") val imageUrlPath : String
    )

}