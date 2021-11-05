package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QualityRequest (
    @SerializedName("user_id") var userId : String,
    @SerializedName("contact_area_id") var contactAreaId : Int,
    @SerializedName("service_stars") var serviceStars : Int ,
    @SerializedName("effective") var effective : Boolean
): Serializable