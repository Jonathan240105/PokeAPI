package com.example.pokenexusapplication.Data.LocalData.PokemonData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import com.example.pokenexusapplication.Data.RemoteData.Responses.Stat
import com.example.pokenexusapplication.Data.RemoteData.Responses.Tipo
import com.example.pokenexusapplication.Data.RemoteData.Responses.TipoPokemon
import com.example.pokenexusapplication.Domain.Especie
import com.example.pokenexusapplication.Domain.Pokemon
import com.example.pokenexusapplication.Domain.getDescripcionDeLista

@Entity(tableName = "Pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int = 0,
    val nombre: String = "",
    val tipos: List<TipoEntity>? = null,
    val fotoUrl: Imagen? = null,
    val peso: Int = 0,
    val altura: Int = 0,
    val estadisticas: List<Stat>? = null,
    val idEspecie: Int? = null,
    var descripcion: String? = ""
)

data class TipoEntity(
    val slot: Int,
    val nombre: String
)

fun PokemonToEntity(pokemon: Pokemon, especie: Especie? = null): PokemonEntity {


    val descripcion = getDescripcionDeLista(especie?.descripcion)
    val idEspecie = pokemon.especie?.url
        ?.trimEnd('/')
        ?.split("/")
        ?.lastOrNull()
        ?.toIntOrNull()

    return PokemonEntity(
        id = pokemon.id,
        nombre = pokemon.nombre,
        peso = pokemon.peso,
        altura = pokemon.altura,
        fotoUrl = pokemon.fotoUrl,
        estadisticas = pokemon.estadisticas,
        tipos = pokemon.tipos?.map {
            TipoEntity(it.slot, it.tipo.nombre)
        },
        idEspecie = idEspecie,
        descripcion = descripcion
    )
}

fun EntityToPokemon(entity: PokemonEntity): Pokemon {
    return Pokemon(
        id = entity.id,
        nombre = entity.nombre,
        peso = entity.peso,
        altura = entity.altura,
        fotoUrl = entity.fotoUrl,
        estadisticas = entity.estadisticas,
        tipos = entity.tipos?.map {
            TipoPokemon(
                slot = it.slot,
                tipo = Tipo(it.nombre)
            )
        },
    )
}

