package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ImagenModelTest {

    @Test
    fun `Creacion de Imagen con URL`() {
        val url = "url"
        val imagen = Imagen(frontDefault = url)

        assertEquals(url, imagen.frontDefault)
    }

    @Test
    fun `Actualizar un atributo de Imagen`() {
        val img = Imagen(frontDefault = "url1")
        val imgCopia = img.copy(frontDefault = "url2")


        assertEquals("url2", imgCopia.frontDefault)
    }
}