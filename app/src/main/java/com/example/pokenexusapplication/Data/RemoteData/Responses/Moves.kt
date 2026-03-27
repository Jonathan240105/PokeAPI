package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class Move(
    @SerializedName("move") val nombre: MoveCompacto
)

data class MoveCompacto(
    @SerializedName("name") val nombre: String
)

