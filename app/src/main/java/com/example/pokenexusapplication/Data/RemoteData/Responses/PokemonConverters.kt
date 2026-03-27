package com.example.pokenexusapplication.Data.RemoteData.Responses

import androidx.room.TypeConverter
import com.example.pokenexusapplication.Data.LocalData.PokemonData.TipoEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PokemonConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromSprites(imagen: Imagen?): String? {
        return gson.toJson(imagen)
    }

    @TypeConverter
    fun toSprites(json: String?): Imagen? {
        return gson.fromJson(json, Imagen::class.java)
    }

    @TypeConverter
    fun fromStatsList(stats: List<Stat>?): String? {
        return gson.toJson(stats)
    }

    @TypeConverter
    fun toStatsList(json: String?): List<Stat>? {
        val type = object : TypeToken<List<Stat>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromPokemonTipoList(tipos: List<TipoEntity>?): String? {
        return gson.toJson(tipos)
    }

    @TypeConverter
    fun toPokemonTipoList(json: String?): List<TipoEntity>? {
        val type = object : TypeToken<List<TipoEntity>>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
}