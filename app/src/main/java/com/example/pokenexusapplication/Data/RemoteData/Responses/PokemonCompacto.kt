package com.example.pokenexusapplication.Data.RemoteData.Responses

data class PokemonCompactoList(
    val results: List<PokemonCompacto> = emptyList()
)

data class PokemonCompacto(
    val name: String = "",
    val url: String = ""
)