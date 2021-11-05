package com.liderman.mundolidermanapp.domain.entity.login

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginEntity(
    @SerializedName("lastNames") val lastNames: String,
    @SerializedName("position") var position: String,
    @SerializedName("client") var client: String,
    @SerializedName("user_id") val userId: String,
    @SerializedName("dni") val dni: String,
    @SerializedName("names") val names: String,
    @SerializedName("token") var token: String,
    @SerializedName("email") var email: String?,
    @SerializedName("phone") var phone: String?,
    @SerializedName("address") var address: String?,
    @SerializedName("number_badges") var numberBadges: String?,
    @SerializedName("points") var points: String?,
    @SerializedName("photo_url") var photoUrl: String?,
    @SerializedName("photo_name") var photoName: String?,
    @SerializedName("unidad") var unidad: String,
    @SerializedName("zona") var zona: String,
    @SerializedName("sexo") var sexo: String,
    @SerializedName("edad") var edad: String,
    @SerializedName("fecha_ingreso") var fechaIngreso : String
) : Serializable {

    companion object {

        fun default(): LoginEntity {
            return LoginEntity(
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }
    }


}



