package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.RemoteData.Responses.Move
import com.example.pokenexusapplication.Data.RemoteData.Responses.MoveCompacto
import org.junit.Assert.assertEquals
import org.junit.Test

class MovesTest {

    @Test
    fun `Creación de MooveCompacto`() {
        val nombreAtaque = "impactrueno"
        val moveCompacto = MoveCompacto(nombre = nombreAtaque)

        val move = Move(moveCompacto)

        assertEquals(nombreAtaque, move.nombre.nombre)
    }

    @Test
    fun `Actualizar un atributo de move`() {
        val Movimiento = Move(MoveCompacto("tackle"))

        val copia = Movimiento.copy(nombre = MoveCompacto("scratch"))

        assertEquals("scratch", copia.nombre.nombre)
        assertEquals("tackle", Movimiento.nombre.nombre)
    }

    @Test
    fun `Dos objetos con los mismos datos deben ser iguales`() {
        val original = Move(MoveCompacto("fly"))

        val duplicado = original.copy()

        assertEquals(original, duplicado)
    }
}