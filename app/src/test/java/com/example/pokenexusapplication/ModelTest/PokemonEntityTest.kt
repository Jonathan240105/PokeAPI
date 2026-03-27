package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.LocalData.PokemonData.EntityToPokemon
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.PokemonToEntity
import com.example.pokenexusapplication.Data.LocalData.PokemonData.TipoEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.Tipo
import com.example.pokenexusapplication.Data.RemoteData.Responses.TipoPokemon
import com.example.pokenexusapplication.Domain.Pokemon
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PokemonEntityTest {
    @Test
    fun `Creación de PokemonEntity`() {
        val id = 25
        val nombre = "Pikachu"

        val entity = PokemonEntity(id = id, nombre = nombre, peso = 60)

        assertEquals(25, entity.id)
        assertEquals("Pikachu", entity.nombre)
        assertEquals(60, entity.peso)
    }

    @Test
    fun `Actualizar atributos de PokemonEntity`() {
        val original = PokemonEntity(id = 4, nombre = "Charmander", altura = 6)

        val actualizada = original.copy(nombre = "Charmeleon", id = 5)

        assertEquals("Charmeleon", actualizada.nombre)
        assertEquals(5, actualizada.id)
        assertEquals(6, actualizada.altura)
    }

    @Test
    fun `Dos entidades con mismos IDs y datos deben ser iguales`() {
        val entity1 = PokemonEntity(id = 150, nombre = "Mewtwo")
        val entity2 = PokemonEntity(id = 150, nombre = "Mewtwo")

        assertEquals(entity1, entity2)
    }

    @Test
    fun `Creacion de TipoEntity`() {
        val tipo = TipoEntity(slot = 1, nombre = "Electric")

        assertEquals(1, tipo.slot)
        assertEquals("Electric", tipo.nombre)
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

        assertEquals(pokemonDominio.id, entity.id)
        assertEquals(pokemonDominio.nombre, entity.nombre)
        assertEquals(pokemonDominio.peso, entity.peso)
        assertEquals(1, entity.tipos?.size)
        assertEquals("electric", entity.tipos?.get(0)?.nombre)
    }

    @Test
    fun `EntityToPokemon convierte correctamente todos los campos`() {
        val entity = PokemonEntity(
            id = 1,
            nombre = "Bulbasaur",
            tipos = listOf(TipoEntity(slot = 1, nombre = "grass"))
        )

        val pokemon = EntityToPokemon(entity)

        assertEquals(entity.id, pokemon.id)
        assertEquals(entity.nombre, pokemon.nombre)
        assertEquals("grass", pokemon.tipos?.get(0)?.tipo?.nombre)
    }
}