package com.liderman.mundolidermanapp.domain.entity.contact

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContactEntity (
    @SerializedName("id") var id : Int,
    @SerializedName("label") var label : String,
    @SerializedName("number") var number : String
) : Serializable