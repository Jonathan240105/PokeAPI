package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.RemoteData.Responses.Stat
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class StatModelTest {
    @Test
    fun `Verificar creacion de Stat con valor correcto`() {
        val valor = 100

        val stat = Stat(valor = valor)

        assertEquals(valor, stat.valor)
    }

    @Test
    fun `Actualizar un atributo`() {
        val stat = Stat(valor = 50)
        val statCopia = stat.copy(valor = 75)

        assertEquals(75, statCopia.valor)
    }
}