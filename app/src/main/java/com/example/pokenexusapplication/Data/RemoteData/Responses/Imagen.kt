package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class Imagen(
    @SerializedName("front_default")
    val frontDefault: String?
)
