package com.example.pokenexusapplication.Data.LocalData.EvolucionData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Data.RemoteData.Responses.Evolucion
import com.example.pokenexusapplication.Domain.EvolucionModel


@Entity(tableName = "Evolucion")
data class EvolucionEntity(
    @PrimaryKey val id: Int = 0,
    val idCadena: Int = 0,
    val nombrePokemonBase: String = "",
    val nombrePokemonEvolucion: String = "",
    val nivelMinimo: Int = 0
)

fun EntityToEvolucion(lista: List<EvolucionEntity>?): List<EvolucionModel> {

    if (lista == null) return emptyList()

    return lista.map { entity ->
        EvolucionModel(
            id = entity.id,
            idCadena = entity.idCadena,
            nombrePokemonBase = entity.nombrePokemonBase,
            nombrePokemonEvolucion = entity.nombrePokemonEvolucion,
            nivelMinimo = entity.nivelMinimo
        )
    }
}

fun EvolucionesToEntity(apiResponse: Evolucion): List<EvolucionEntity> {
    val lista = mutableListOf<EvolucionEntity>()
    val idCadena = apiResponse.id
    val base = apiResponse.cadenaDeEvolucion

    base.evolucion.forEach { evo1 ->
        lista.add(
            EvolucionEntity(
                idCadena = idCadena,
                nombrePokemonBase = base.pokemon.nombre,
                nombrePokemonEvolucion = evo1.pokemon.nombre,
                nivelMinimo = evo1.detallesEvolucion.firstOrNull()?.nivelMinimo ?: 0
            )
        )

        evo1.evolucion.forEach { evo2 ->
            lista.add(
                EvolucionEntity(
                    idCadena = idCadena,
                    nombrePokemonBase = evo1.pokemon.nombre,
                    nombrePokemonEvolucion = evo2.pokemon.nombre,
                    nivelMinimo = evo2.detallesEvolucion.firstOrNull()?.nivelMinimo ?: 0
                )
            )
        }
    }
    return lista
}