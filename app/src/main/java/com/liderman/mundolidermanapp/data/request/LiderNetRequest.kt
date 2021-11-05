package com.liderman.mundolidermanapp.data.request

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.lidernet.AnswerLidernetEntity
import java.io.Serializable

data class LiderNetRequest(
    @SerializedName("user_id") var userId : String,
    @SerializedName("lidernet_id") var lidernet_id : Int,
    @SerializedName("answers") var answers : ArrayList<AnswerLidernetEntity>
):Serializable