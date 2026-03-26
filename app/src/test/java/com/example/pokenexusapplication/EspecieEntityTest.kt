package com.example.pokenexusapplication

import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieEntity
import com.example.pokenexusapplication.Data.LocalData.EspecieData.EspecieToEntity
import com.example.pokenexusapplication.Domain.CadenaEvoluciones
import com.example.pokenexusapplication.Domain.Especie
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EspecieEntityTest {
    @Test
    fun `Creacion de EspecieEntity con valores correctos`() {
        val idPrueba = 25
        val idEvolucionPrueba = 10

        val entity = EspecieEntity(id = idPrueba, idEvoluciones = idEvolucionPrueba)

        assertEquals(25, entity.id)
        assertEquals(10, entity.idEvoluciones)
    }

    @Test
    fun `Actualizar atributos de especie`() {
        val original = EspecieEntity(id = 1, idEvoluciones = 5)

        val modificado = original.copy(idEvoluciones = 8)

        assertEquals(8, modificado.idEvoluciones)
        assertEquals(1, modificado.id)
    }

    @Test
    fun `Dos entidades con los mismos datos deben ser iguales (duplicado)`() {
        val entity1 = EspecieEntity(id = 150, idEvoluciones = 78)
        val entity2 = EspecieEntity(id = 150, idEvoluciones = 78)

        assertEquals(entity1, entity2)
        assertEquals(entity1.hashCode(), entity2.hashCode())
    }

    @Test
    fun `EspecieToEntity debe extraer el id correctamente de la urlde evolucion`() {
        val mockEspecie = Especie(
            id = 25,
            cadenaEvolucion = CadenaEvoluciones(
                url = "https://pokeapi.co/api/v2/evolution-chain/10/"
            )
        )

        val resultado = EspecieToEntity(mockEspecie)

        assertEquals(25, resultado.id)
        assertEquals(10, resultado.idEvoluciones)
    }

    @Test
    fun `EspecieToEntity debe devolver null si la URL esta mal formada`() {
        val mockEspecieError = Especie(
            id = 1,
            cadenaEvolucion = CadenaEvoluciones(url = "url-sin-sentido")
        )

        val resultado = EspecieToEntity(mockEspecieError)

        assertEquals(null, resultado.idEvoluciones)
    }
}