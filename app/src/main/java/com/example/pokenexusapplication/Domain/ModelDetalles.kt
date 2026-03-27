package com.example.pokenexusapplication.Domain

data class ModelDetalles(
    val pokemonActual: Pokemon = Pokemon(),
    val listaEvoluciones: List<EvolucionModel> = emptyList()
)