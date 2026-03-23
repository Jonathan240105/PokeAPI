package com.example.pokenexusapplication.Data.LocalData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prueba")
data class Prueba(
    @PrimaryKey
    val id: Int = 0,
)
