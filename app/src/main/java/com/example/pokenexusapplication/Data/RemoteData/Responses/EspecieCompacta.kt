package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class EspecieCompacta(
    @SerializedName("name") val nombre: String,
    @SerializedName("url") val url: String
)
