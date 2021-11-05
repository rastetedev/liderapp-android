package com.liderman.mundolidermanapp.domain.entity.announcements

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnnouncementEntity (
    @SerializedName("id") val id : Int,
    @SerializedName("image_url") val imageUrl : String,
    @SerializedName("is_main") val isMain : Int,
    @SerializedName("order") val order : Int
) : Serializable