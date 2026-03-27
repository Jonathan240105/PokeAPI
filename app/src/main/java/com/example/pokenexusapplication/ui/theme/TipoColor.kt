package com.example.pokenexusapplication.ui.theme

import androidx.compose.ui.graphics.Color


fun getColorPokemon(tipo: String): Color {
    return when (tipo.lowercase()) {
        "fire" -> PokeRojo
        "grass" -> PokeVerde
        "water" -> PokeAzul
        "normal" -> PokeNaranja
        "bug" -> PokeLila
        "poison" -> PokeMorado
        "fairy" -> PokeMorado2
        "ground" -> PokeMarron
        "fighting" -> PokeMarron2
        "psychic" -> PokeMorado3
        "rock" -> PokeGris
        "steel" -> PokeGrisOscuro
        "ghost" -> pokeFluorescente
        "electric" -> pokeAmarillo
        "ice" -> pokeAzulElectrico
        "dragon" -> pokeAzu
        "dark" -> pokeVerdeOscuro
        "stellar" -> pokeDorado
        "flying" -> pokeAzulClaro
        else -> Color.Black
    }
}