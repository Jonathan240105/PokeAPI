package com.example.pokenexusapplication.Data.LocalData

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    version = 0,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase(){
}