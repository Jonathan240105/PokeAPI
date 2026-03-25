package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonData.EntityToPokemon
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.TipoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.Tipo
import com.example.pokenexusapplication.Data.RemoteData.Responses.TipoPokemon
import com.example.pokenexusapplication.Domain.Pokemon
import junit.framework.TestCase
import org.junit.Test

class PokemonEntityModelTest {
    @Test
    fun `Creación de PokemonEntity`() {
        val id = 25
        val nombre = "Pikachu"

        val entity = PokemonEntity(id = id, nombre = nombre, peso = 60)

        TestCase.assertEquals(25, entity.id)
        TestCase.assertEquals("Pikachu", entity.nombre)
        TestCase.assertEquals(60, entity.peso)
    }

    @Test
    fun `Actualizar atributos de PokemonEntity`() {
        val original = PokemonEntity(id = 4, nombre = "Charmander", altura = 6)

        val actualizada = original.copy(nombre = "Charmeleon", id = 5)

        TestCase.assertEquals("Charmeleon", actualizada.nombre)
        TestCase.assertEquals(5, actualizada.id)
        TestCase.assertEquals(6, actualizada.altura)
    }

    @Test
    fun `Dos entidades con mismos IDs y datos deben ser iguales`() {
        val entity1 = PokemonEntity(id = 150, nombre = "Mewtwo")
        val entity2 = PokemonEntity(id = 150, nombre = "Mewtwo")

        TestCase.assertEquals(entity1, entity2)
    }

    @Test
    fun `Creacion de TipoEntity`() {
        val tipo = TipoEntity(slot = 1, nombre = "Electric")

        TestCase.assertEquals(1, tipo.slot)
        TestCase.assertEquals("Electric", tipo.nombre)
    }

    @Test
    fun `PokemonToEntity convierte correctamente todos los campos`() {
        val pokemonDominio = Pokemon(
            id = 25,
            nombre = "Pikachu",
            peso = 60,
            altura = 4,
            tipos = listOf(TipoPokemon(slot = 1, tipo = Tipo("electric")))
        )

        val entity = PokemonToEntity(pokemonDominio)

        TestCase.assertEquals(pokemonDominio.id, entity.id)
        TestCase.assertEquals(pokemonDominio.nombre, entity.nombre)
        TestCase.assertEquals(pokemonDominio.peso, entity.peso)
        TestCase.assertEquals(1, entity.tipos?.size)
        TestCase.assertEquals("electric", entity.tipos?.get(0)?.nombre)
    }

    @Test
    fun `EntityToPokemon convierte correctamente todos los campos`() {
        val entity = PokemonEntity(
            id = 1,
            nombre = "Bulbasaur",
            tipos = listOf(TipoEntity(slot = 1, nombre = "grass"))
        )

        val pokemon = EntityToPokemon(entity)

        TestCase.assertEquals(entity.id, pokemon.id)
        TestCase.assertEquals(entity.nombre, pokemon.nombre)
        TestCase.assertEquals("grass", pokemon.tipos?.get(0)?.tipo?.nombre)
    }
}