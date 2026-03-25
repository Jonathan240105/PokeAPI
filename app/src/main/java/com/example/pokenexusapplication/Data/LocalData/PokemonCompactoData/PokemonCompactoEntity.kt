package com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompacto

@Entity(tableName = "PokemonCompacto")
data class PokemonCompactoEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val url: String
)

fun CompactoToEntity(pokemon: PokemonCompacto): PokemonCompactoEntity {
    val id = pokemon.url
        .trimEnd('/')
        .split("/")
        .last()
        .toInt()

    return PokemonCompactoEntity(
        id = id,
        name = pokemon.name,
        url = pokemon.url
    )
}

fun EntityToCompacto(entity: PokemonCompactoEntity): PokemonCompacto {
    return PokemonCompacto(
        name = entity.name,
        url = entity.url
    )
}