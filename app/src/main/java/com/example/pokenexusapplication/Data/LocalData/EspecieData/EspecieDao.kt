package com.example.pokenexusapplication.Data.LocalData.EspecieData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import retrofit2.http.Path

@Dao
interface EspecieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEspecie(especie: EspecieEntity)

    @Query("SELECT idEvoluciones FROM Especie WHERE id = :idPokemon")
    suspend fun getIdEvoluciones(
        @Path("idPokemon") idPokemon: Int
    ): Int

    @Query("Select * from Especie where id = :idEspecie")
    suspend fun getEspeciePorId(
        @Path("idEspecie")idEspecie: Int): EspecieEntity?
}