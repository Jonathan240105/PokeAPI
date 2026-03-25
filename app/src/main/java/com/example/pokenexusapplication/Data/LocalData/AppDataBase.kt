package com.example.pokenexusapplication.Data.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoDao
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonDao
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonConverters

@Database(
    entities = [ PokemonEntity::class, PokemonCompactoEntity::class],
    version = 4,
    exportSchema = false
)
@TypeConverters(PokemonConverters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
    abstract fun pokemonCompactoDao(): PokemonCompactoDao
}