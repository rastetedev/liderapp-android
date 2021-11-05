package com.liderman.mundolidermanapp.data.responses

import com.google.gson.annotations.SerializedName
import com.liderman.mundolidermanapp.domain.entity.announcements.AnnouncementEntity
import java.io.Serializable

data class AnnouncementResponse(
    @SerializedName("Mensaje") val message: String,
    @SerializedName("Resultado") val result: List<AnnouncementEntity>?
) : Serializable