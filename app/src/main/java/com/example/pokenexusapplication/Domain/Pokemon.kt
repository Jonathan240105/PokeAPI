package com.example.pokenexusapplication.Domain

import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
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
    @SerializedName("stats") val `estadísticas`: List<Stat>? = null

)