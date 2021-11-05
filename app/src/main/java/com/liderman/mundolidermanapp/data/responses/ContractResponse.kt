package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.contract.ContractEntity
import java.io.Serializable

data class ContractResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: ContractEntity?
) : Serializable