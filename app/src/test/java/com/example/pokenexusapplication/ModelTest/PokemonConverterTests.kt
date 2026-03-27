package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonData.TipoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.Foto
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonConverters
import com.example.pokenexusapplication.Data.RemoteData.Responses.Stat
import com.example.pokenexusapplication.Data.RemoteData.Responses.urlFoto
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonConverterTests {

    private val converters = PokemonConverters()


    //Tests que comprueban la funcionalidad de los convertidores.
    //Funcionan como puente entre objetos json y el leguaje de android.
    //Los utilizo para guardar los objetos json en la base de datos. SOlo los ocupo para objetos pequeños
    @Test
    fun `Debe convertir Imagen a JSON y al reves`() {
        val imagen = Imagen(Foto(urlFoto("https://pokeapi.co/25.png")))

        val json = converters.fromSprites(imagen)
        val resultado = converters.toSprites(json)

        assertEquals(imagen, resultado)
    }

    @Test
    fun `Debe convertir lista de Stats a JSON y viceversa`() {
        val listaStats = listOf(Stat(valor = 45), Stat(valor = 60))

        val json = converters.fromStatsList(listaStats)
        val resultado = converters.toStatsList(json)

        assertEquals(2, resultado?.size)
        assertEquals(45, resultado?.get(0)?.valor)
        assertEquals(listaStats, resultado)
    }

    @Test
    fun `Debe convertir lista de TipoEntity a JSON y viceversa`() {
        val listaTipos = listOf(
            TipoEntity(1, "electric"),
            TipoEntity(2, "steel")
        )

        val json = converters.fromPokemonTipoList(listaTipos)
        val resultado = converters.toPokemonTipoList(json)

        assertEquals(2, resultado?.size)
        assertEquals("electric", resultado?.get(0)?.nombre)
        assertEquals(listaTipos, resultado)
    }

    @Test
    fun `Debe manejar Imagen nula devolviendo null o json nulo`() {
        val json = converters.fromSprites(null)
        val resultado = converters.toSprites(null)

        assertEquals("null", json)
        assertEquals(null, resultado)
    }

    @Test
    fun `Debe manejar lista de Stats nula`() {
        val json = converters.fromStatsList(null)
        val resultado = converters.toStatsList(null)

        assertEquals("null", json)
        assertEquals(null, resultado)
    }
}