package com.liderman.mundolidermanapp.domain.entity.lidernet

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LiderNetEntity(
    @SerializedName("lidernet") val lidernet : Lidernet,
    @SerializedName("lidernet_questions") val lidernetQuestions : List<LidernetQuestions>,
    @SerializedName("answered") val answered : Boolean
) : Serializable {

    data class Lidernet (
        @SerializedName("id") val id : Int,
        @SerializedName("main_video_url") val mainVideoUrl : String
    ) : Serializable

    data class LidernetQuestions (
        @SerializedName("id") val id : Int,
        @SerializedName("lidernet_id") val lidernetId : Int,
        @SerializedName("question") val question : String ,
        @SerializedName("answer_one") val answerOne : String,
        @SerializedName("answer_two") val answerTwo : String ,
        @SerializedName("answer_three") val answerThree : String,
        @SerializedName("answer_four") val answerFour : String
    ) : Serializable
}