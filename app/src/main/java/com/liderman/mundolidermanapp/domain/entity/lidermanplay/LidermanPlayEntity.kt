package com.liderman.mundolidermanapp.domain.entity.lidermanplay

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LidermanPlayEntity (
    @SerializedName("id") val id : Int = 0,
    @SerializedName("video_url") val videoUrl : String = "",
    @SerializedName("is_main") val isMain : Int = 0
) : Serializable