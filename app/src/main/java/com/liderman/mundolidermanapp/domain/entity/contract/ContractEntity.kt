package com.liderman.mundolidermanapp.domain.entity.contract

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ContractEntity(
    @SerializedName("id") var id: Int,
    @SerializedName("state") var state: Int,
    @SerializedName("contract_url") var url: String
) : Serializable {

    companion object {

        fun default(): ContractEntity {
            return ContractEntity(
                0,
                0,
                ""
            )
        }
    }

}