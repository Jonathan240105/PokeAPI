package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.CompactoToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.EntityToCompacto
import com.example.pokenexusapplication.Data.LocalData.PokemonCompactoData.PokemonCompactoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompacto
import com.example.pokenexusapplication.Data.RemoteData.Responses.PokemonCompactoList
import junit.framework.TestCase
import org.junit.Test

class PokemonCompactoEntityTest {

    @Test
    fun `Creacion de la entidad con valores correctos`() {
        val entity = PokemonCompactoEntity(id = 1, name = "Bulbasaur", url = "url")

        TestCase.assertEquals(1, entity.id)
        TestCase.assertEquals("Bulbasaur", entity.name)
        TestCase.assertEquals("url", entity.url)
    }

    @Test
    fun `Actualizar atributos de la clasee PokemonCompactoEnrtituy`() {
        val original = PokemonCompactoEntity(id = 1, name = "Bulbasaur", url = "url/1/")

        val copia = original.copy(name = "Ivysaur")

        TestCase.assertEquals("Ivysaur", copia.name)
        TestCase.assertEquals(original.id, copia.id)
        TestCase.assertEquals(original.url, copia.url)
    }

    @Test
    fun `Dos entidades con los mismos datos por l tanto son iguales`() {
        val entity1 = PokemonCompactoEntity(id = 25, name = "Pikachu", url = "url/25/")
        val entity2 = PokemonCompactoEntity(id = 25, name = "Pikachu", url = "url/25/")

        TestCase.assertEquals(entity1, entity2)
        TestCase.assertEquals(entity1.hashCode(), entity2.hashCode())
    }

    @Test
    fun `CompactoToEntity extrae el ID correctamente de la URL`() {
        val compacto = PokemonCompacto(
            name = "bulbasaur",
            url = "https://pokeapi.co/api/v2/pokemon/1/"
        )

        val entity = CompactoToEntity(compacto)

        TestCase.assertEquals(1, entity.id)
        TestCase.assertEquals("bulbasaur", entity.name)
    }

    @Test
    fun `EntityToCompacto mantiene los datos originales`() {
        val entity = PokemonCompactoEntity(
            id = 25,
            name = "pikachu",
            url = "https://pokeapi.co/api/v2/pokemon/25/"
        )

        val compacto = EntityToCompacto(entity)

        TestCase.assertEquals("pikachu", compacto.name)
        TestCase.assertEquals("https://pokeapi.co/api/v2/pokemon/25/", compacto.url)
    }

    @Test
    fun `Creacion con una lista de pokemons`() {
        val listaDePrueba = listOf(
            PokemonCompacto("Bulbasaur", "url/1"),
            PokemonCompacto("Ivysaur", "url/2")
        )

        val contenedor = PokemonCompactoList(listaDePrueba)

        TestCase.assertEquals(2, contenedor.results.size)
        TestCase.assertEquals("Bulbasaur", contenedor.results[0].name)
    }

    @Test
    fun `Actualizar la lista`() {
        val original = PokemonCompactoList(listOf(PokemonCompacto("1", "u1")))
        val nuevaLista = listOf(PokemonCompacto("2", "u2"))

        val copia = original.copy(results = nuevaLista)

        TestCase.assertEquals(1, copia.results.size)
        TestCase.assertEquals("2", copia.results[0].name)
    }
}