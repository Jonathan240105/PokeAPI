package com.example.pokenexusapplication.Data.LocalData.EvolucionData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EvolucionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvoluciones(lista: List<EvolucionEntity>)

    @Query("Select * from Evolucion where idCadena = :idCadenaEvolucion")
    suspend fun getEvoluciones(idCadenaEvolucion: Int): List<EvolucionEntity>
}