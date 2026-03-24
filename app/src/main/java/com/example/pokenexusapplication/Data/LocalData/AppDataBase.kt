package com.example.pokenexusapplication.Data.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Prueba::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase(){
}