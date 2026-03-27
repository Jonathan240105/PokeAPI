package com.example.pokenexusapplication.MapperTest

import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EntityToEvolucion
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionEntity
import com.example.pokenexusapplication.Data.LocalData.EvolucionData.EvolucionesToEntity
import com.example.pokenexusapplication.Data.RemoteData.Responses.CadenaEvolucion
import com.example.pokenexusapplication.Data.RemoteData.Responses.DetallesEvolucion
import com.example.pokenexusapplication.Data.RemoteData.Responses.Evolucion
import com.example.pokenexusapplication.Data.RemoteData.Responses.ObjetoCompacto
import org.junit.Assert.assertEquals
import org.junit.Test

class EvolutionEntityTest {


    @Test
    fun `EntityToEvolucion debe mapear correctamente los campos de la lista`() {
        val listaEntities = listOf(
            EvolucionEntity(1, 10, "Pikachu", "Raichu", 0),
            EvolucionEntity(2, 10, "Pichu", "Pikachu", 0)
        )

        val resultado = EntityToEvolucion(listaEntities)

        assertEquals(2, resultado.size)
        assertEquals("Pikachu", resultado[0].nombrePokemonBase)
        assertEquals("Raichu", resultado[0].nombrePokemonEvolucion)
        assertEquals(10, resultado[0].idCadena)
    }

    @Test
    fun `EvolucionesToEntity debe aplanar una cadena de 3 niveles correctamente`() {
        val mockApiResponse = Evolucion(
            id = 1,
            cadenaDeEvolucion = CadenaEvolucion(
                pokemon = ObjetoCompacto("bulbasaur", ""),
                detallesEvolucion = emptyList(),
                evolucion = listOf(
                    CadenaEvolucion(
                        pokemon = ObjetoCompacto("ivysaur", ""),
                        detallesEvolucion = listOf(DetallesEvolucion(16)),
                        evolucion = listOf(
                            CadenaEvolucion(
                                pokemon = ObjetoCompacto("venusaur", ""),
                                detallesEvolucion = listOf(DetallesEvolucion(32)),
                                evolucion = emptyList()
                            )
                        )
                    )
                )
            )
        )

        val resultado = EvolucionesToEntity(mockApiResponse)

        assertEquals(2, resultado.size)

        assertEquals("bulbasaur", resultado[0].nombrePokemonBase)
        assertEquals("ivysaur", resultado[0].nombrePokemonEvolucion)
        assertEquals(16, resultado[0].nivelMinimo)
        assertEquals("ivysaur", resultado[1].nombrePokemonBase)
        assertEquals("venusaur", resultado[1].nombrePokemonEvolucion)
        assertEquals(32, resultado[1].nivelMinimo)
    }
}