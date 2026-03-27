package com.example.pokenexusapplication.Data.RemoteData

import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompactoList
import com.example.pokenexusapplication.Data.RemoteData.Variables.Endpoints
import com.example.pokenexusapplication.Domain.Especie
import com.example.pokenexusapplication.Data.RemoteData.Responses.Evolucion
import com.example.pokenexusapplication.Domain.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DataInterface {

    @GET(Endpoints.ListarPokemon)
    suspend fun listarPokemons(): Response<PokemonCompactoList>

    @GET(Endpoints.BuscarPokemon)
    suspend fun pillarUnPokemon(
        @Path("name") nombrePokemon: String
    ): Response<Pokemon>

    @GET(Endpoints.BuscarEspecie)
    suspend fun getEspecie(
        @Path("id") id: Int?
    ): Response<Especie>

    @GET(Endpoints.BuscarEvolucion)
    suspend fun getEvolucion(
        @Path("id") id: Int?
    ): Response<Evolucion>

}