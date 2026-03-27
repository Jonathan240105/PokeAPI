package com.example.pokenexusapplication.Data.LocalData.EvolucionData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokenexusapplication.Data.LocalData.Querys

@Dao
interface EvolucionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEvoluciones(lista: List<EvolucionEntity>)

    @Query(Querys.selectEvoluciones)
    suspend fun getEvoluciones(idCadenaEvolucion: Int): List<EvolucionEntity>
}