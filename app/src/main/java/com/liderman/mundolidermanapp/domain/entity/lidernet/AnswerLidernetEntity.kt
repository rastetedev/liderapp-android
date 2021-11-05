package com.liderman.mundolidermanapp.domain.entity.lidernet

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnswerLidernetEntity(
    @SerializedName("id") var id : Int,
    @SerializedName("value") var value : Int
): Serializable
