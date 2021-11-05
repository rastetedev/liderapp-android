package com.liderman.mundolidermanapp.domain.entity.borrow

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BorrowEntity (
    @SerializedName("Comentario") val comment : String,
    @SerializedName("FechaAprobacion") val approveDate : String,
    @SerializedName("MontoPrestamo") val borrowAmount : String,
    @SerializedName("SaldoDeuda") val restAmount : String,
    @SerializedName("TipoPrestamo") val borrowType : String
) : Serializable