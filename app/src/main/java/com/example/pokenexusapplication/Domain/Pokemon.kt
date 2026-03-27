package com.example.pokenexusapplication.Domain

import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Data.RemoteData.Responses.EspecieCompacta
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import com.example.pokenexusapplication.Data.RemoteData.Responses.Move
import com.example.pokenexusapplication.Data.RemoteData.Responses.Stat
import com.example.pokenexusapplication.Data.RemoteData.Responses.TipoPokemon
import com.google.gson.annotations.SerializedName

data class Pokemon(
    @PrimaryKey @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val nombre: String = "",
    @SerializedName("types") val tipos: List<TipoPokemon>? = null,
    @SerializedName("sprites") val fotoUrl: Imagen? = null,
    @SerializedName("weight") val peso: Int = 0,
    @SerializedName("height") val altura: Int = 0,
    @SerializedName("stats") val estadisticas: List<Stat>? = null,
    @SerializedName("species") val especie: EspecieCompacta? = null,
    @SerializedName("moves") val movimientos: List<Move>? = null, // <-- AÑADIDO
    val descripcion: String = "",
    val idEspecie: Int? = 0,
    val listaNombresMovimientos: List<String> = emptyList()

)