package com.liderman.mundolidermanapp.domain.entity.amatrata

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AmaTratoEntity (
    @SerializedName("id") val id : Int,
    @SerializedName("label") val label : String,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("web_redirect_inscription") val webRedirectInscription : String,
    @SerializedName("requirements") val requirements : String,
    @SerializedName("order") val order : Int
): Serializable