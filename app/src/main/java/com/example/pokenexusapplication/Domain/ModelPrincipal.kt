package com.example.pokenexusapplication.Domain

import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompacto

data class ModelPrincipal(
    val listaPokemonsCompactos: List<PokemonCompacto> = emptyList(),
    val isLoading: Boolean = true,
    val succes: Boolean = false,
    val listaPokemons: List<Pokemon> = emptyList()
)
