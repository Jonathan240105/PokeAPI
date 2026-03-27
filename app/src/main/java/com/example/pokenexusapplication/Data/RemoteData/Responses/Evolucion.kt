package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class Evolucion(
    @SerializedName("id") val id: Int,
    @SerializedName("chain") val cadenaDeEvolucion: CadenaEvolucion
)

data class CadenaEvolucion(
    @SerializedName("species") val pokemon: ObjetoCompacto,
    @SerializedName("evolves_to") val evolucion: List<CadenaEvolucion>,
    @SerializedName("evolution_details") val detallesEvolucion: List<DetallesEvolucion>
)

data class ObjetoCompacto(
    @SerializedName("name") val nombre: String,
    @SerializedName("url") val url: String
)

data class DetallesEvolucion(
    @SerializedName("min_level") val nivelMinimo: Int
)