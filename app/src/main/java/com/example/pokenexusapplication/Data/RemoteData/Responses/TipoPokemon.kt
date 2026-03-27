package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class TipoPokemon(
    val slot: Int,
    @SerializedName("type")val tipo: Tipo
)

data class Tipo(
    @SerializedName("name") val nombre: String
)