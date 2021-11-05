package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.contact.ContactEntity
import java.io.Serializable

data class ContactResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<ContactEntity>?
) : Serializable