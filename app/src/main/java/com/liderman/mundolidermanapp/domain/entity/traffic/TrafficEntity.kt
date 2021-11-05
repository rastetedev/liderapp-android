package com.liderman.mundolidermanapp.domain.entity.traffic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TrafficEntity(
    @SerializedName("is_on_lidernet") val isOnLidernet : String, //0,1
    @SerializedName("is_on_tasks") val isOnTasks : String, //0,1
    @SerializedName("is_on_borrows") val isOnBorrows : String, //0,1
    @SerializedName("is_on_esl") val isOnEsl : String, //1,2 1:Verde , 2:Rojo
    @SerializedName("is_on_valor") val is_on_valor : String //0,1
)  : Serializable