package com.example.pokenexusapplication.Data.LocalData.EspecieData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EspecieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEspecie(especie: EspecieEntity)

    @Query("SELECT idEvoluciones FROM Especie WHERE id = :idPokemon")
    suspend fun getIdEvoluciones(idPokemon: Int): Int
}