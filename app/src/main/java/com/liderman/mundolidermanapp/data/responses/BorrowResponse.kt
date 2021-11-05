package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.borrow.BorrowEntity
import java.io.Serializable

data class BorrowResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<BorrowEntity>?
) : Serializable