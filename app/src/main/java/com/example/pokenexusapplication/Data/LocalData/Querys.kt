package com.example.pokenexusapplication.Data.LocalData

object Querys {
    const val selectAllPokemonCompactoPagina =
        "select * from pokemoncompacto order by id asc limit :limit offset :offset"
    const val selectAllPokemonCompacto = "select * from pokemoncompacto order by id asc"
    const val numeroDePokemonsCompacto = "select count(*) from pokemoncompacto"

    const val selectUnPokemon = "Select * from pokemon where nombre = :nombrePokemon limit 1"
    const val selectAllPokemon = "Select * from pokemon"
    const val selectEspecie = "Select * from Especie where id = :idEspecie"
    const val selectIdEvoluciones = "SELECT idEvoluciones FROM Especie WHERE id = :idPokemon"
    const val selectEvoluciones = "Select * from Evolucion where idCadena = :idCadenaEvolucion"
}