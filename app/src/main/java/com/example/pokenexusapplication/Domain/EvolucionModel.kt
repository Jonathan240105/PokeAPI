package com.example.pokenexusapplication.Domain

data class EvolucionModel(
    val id: Int = 0,
    val idCadena: Int = 0,
    val nombrePokemonBase: String = "",
    val nombrePokemonEvolucion: String = "",
    val nivelMinimo: Int = 0
)
