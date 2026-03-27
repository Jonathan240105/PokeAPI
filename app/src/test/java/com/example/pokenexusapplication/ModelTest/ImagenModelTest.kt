package com.example.pokenexusapplication.ModelTest

import com.example.pokenexusapplication.Data.RemoteData.Responses.Foto
import com.example.pokenexusapplication.Data.RemoteData.Responses.Imagen
import com.example.pokenexusapplication.Data.RemoteData.Responses.urlFoto
import junit.framework.TestCase
import junit.framework.TestCase.assertEquals
import org.junit.Test

class ImagenModelTest {

    @Test
    fun `Creacion de Imagen `() {
        val url = "url"
        val imagen = Imagen(Foto(urlFoto(url)))

        assertEquals(url, imagen.other?.officialArtwork?.frontDefault)
    }

    @Test
    fun `Actualizar un atributo de Imagen`() {
        val img = Imagen(Foto(urlFoto("url")))
        val imgCopia = img.copy(Foto(urlFoto("url2")))


        assertEquals("url2", imgCopia.other?.officialArtwork?.frontDefault)
    }
}