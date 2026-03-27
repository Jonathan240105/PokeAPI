package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.CompactoToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.EntityToCompacto
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompacto
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompactoList
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonCompactoEntityTest {

    @Test
    fun `Creacion de la entidad con valores correctos`() {
        val entity = PokemonCompactoEntity(id = 1, name = "Bulbasaur", url = "url")

        assertEquals(1, entity.id)
        assertEquals("Bulbasaur", entity.name)
        assertEquals("url", entity.url)
    }

    @Test
    fun `Actualizar atributos de la clasee PokemonCompactoEnrtituy`() {
        val original = PokemonCompactoEntity(id = 1, name = "Bulbasaur", url = "url/1/")

        val copia = original.copy(name = "Ivysaur")

        assertEquals("Ivysaur", copia.name)
        assertEquals(original.id, copia.id)
        assertEquals(original.url, copia.url)
    }

    @Test
    fun `Dos entidades con los mismos datos por l tanto son iguales`() {
        val entity1 = PokemonCompactoEntity(id = 25, name = "Pikachu", url = "url/25/")
        val entity2 = PokemonCompactoEntity(id = 25, name = "Pikachu", url = "url/25/")

        assertEquals(entity1, entity2)
        assertEquals(entity1.hashCode(), entity2.hashCode())
    }

    @Test
    fun `CompactoToEntity extrae el ID correctamente de la URL`() {
        val compacto = PokemonCompacto(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        )

        val entity = CompactoToEntity(compacto)

        assertEquals(1, entity.id)
        assertEquals("bulbasaur", entity.name)
    }

    @Test
    fun `EntityToCompacto mantiene los datos originales`() {
        val entity = PokemonCompactoEntity(
            id = 25,
            name = "pikachu",
            url = "https://pokeapi.co/api/v2/pokemon/25/"
        )

        val compacto = EntityToCompacto(entity)

        assertEquals("pikachu", compacto.name)
        assertEquals("https://pokeapi.co/api/v2/pokemon/25/", compacto.url)
    }

    @Test
    fun `Creacion con una lista de pokemons`() {
        val listaDePrueba = listOf(
            PokemonCompacto("Bulbasaur", "url/1"),
            PokemonCompacto("Ivysaur", "url/2")
        )

        val contenedor = PokemonCompactoList(listaDePrueba)

        assertEquals(2, contenedor.results.size)
        assertEquals("Bulbasaur", contenedor.results[0].name)
    }

    @Test
    fun `Actualizar la lista`() {
        val original = PokemonCompactoList(listOf(PokemonCompacto("1", "u1")))
        val nuevaLista = listOf(PokemonCompacto("2", "u2"))

        val copia = original.copy(results = nuevaLista)

        assertEquals(1, copia.results.size)
        assertEquals("2", copia.results[0].name)
    }
}