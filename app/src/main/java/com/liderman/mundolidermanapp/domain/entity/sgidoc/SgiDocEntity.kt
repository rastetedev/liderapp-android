package com.liderman.mundolidermanapp.domain.entity.sgidoc

import com.google.gson.annotations.SerializedName

data class SgiDocEntity (
    @SerializedName("id") val id : Int,
    @SerializedName("label") val label : String,
    @SerializedName("icon_url") val iconUrl : String,
    @SerializedName("web_redirect_url") val webRedirectUrl : String,
    @SerializedName("order") val order : Int
)