package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonData.TipoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonConverters
import com.example.pokenexusapplication.Data.RemoteData.Responses.Stat
import junit.framework.TestCase
import org.junit.Test

class PokemonConverterTests {

    private val converters = PokemonConverters()

    @Test
    fun `Debe convertir Imagen a JSON y al reves`() {
        val imagen = Imagen("https://pokeapi.co/25.png")

        val json = converters.fromSprites(imagen)
        val resultado = converters.toSprites(json)

        TestCase.assertEquals(imagen, resultado)
    }

    @Test
    fun `Debe convertir lista de Stats a JSON y viceversa`() {
        val listaStats = listOf(Stat(valor = 45), Stat(valor = 60))

        val json = converters.fromStatsList(listaStats)
        val resultado = converters.toStatsList(json)

        TestCase.assertEquals(2, resultado?.size)
        TestCase.assertEquals(45, resultado?.get(0)?.valor)
        TestCase.assertEquals(listaStats, resultado)
    }

    @Test
    fun `Debe convertir lista de TipoEntity a JSON y viceversa`() {
        val listaTipos = listOf(
            TipoEntity(1, "electric"),
            TipoEntity(2, "steel")
        )

        val json = converters.fromPokemonTipoList(listaTipos)
        val resultado = converters.toPokemonTipoList(json)

        TestCase.assertEquals(2, resultado?.size)
        TestCase.assertEquals("electric", resultado?.get(0)?.nombre)
        TestCase.assertEquals(listaTipos, resultado)
    }
}