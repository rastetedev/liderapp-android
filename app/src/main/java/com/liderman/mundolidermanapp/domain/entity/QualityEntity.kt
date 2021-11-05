package com.liderman.mundolidermanapp.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QualityEntity(
    @SerializedName("id") var id: Int = 0,
    @SerializedName("name") var name: String = "",
    @SerializedName("is_good") var isGood: Boolean? = null
) : Serializable