package com.example.pokenexusapplication.Data.LocalData.EspecieData

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokenexusapplication.Domain.Especie

@Entity(tableName = "Especie")
data class EspecieEntity(
    @PrimaryKey val id: Int,
    val idEvoluciones: Int?
)

fun EspecieToEntity(especie: Especie): EspecieEntity {

    val idEvoluciones = especie.cadenaEvolucion.url
        .trimEnd('/')
        .split("/")
        .lastOrNull()
        ?.toIntOrNull()

    return EspecieEntity(
        id = especie.id,
        idEvoluciones = idEvoluciones
    )

}