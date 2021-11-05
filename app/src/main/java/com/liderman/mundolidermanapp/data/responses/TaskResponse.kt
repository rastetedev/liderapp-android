package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.task.TaskEntity
import java.io.Serializable

data class TaskResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<TaskEntity>?
) : Serializable