package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("base_stat") val valor: Int?
)
