package com.example.pokenexusapplication.Data.LocalData.EspecieData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.pokenexusapplication.Data.LocalData.Querys
import retrofit2.http.Path

@Dao
interface EspecieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarEspecie(especie: EspecieEntity)

    @Query(Querys.selectIdEvoluciones)
    suspend fun getIdEvoluciones(
        @Path("idPokemon") idPokemon: Int
    ): Int

    @Query(Querys.selectEspecie)
    suspend fun getEspeciePorId(
        @Path("idEspecie") idEspecie: Int
    ): EspecieEntity?
}