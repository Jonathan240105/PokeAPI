package com.example.pokenexusapplication.Domain

import com.google.gson.annotations.SerializedName

data class Especie(
    @SerializedName("id") val id: Int = 0,
    @SerializedName("flavor_text_entries") val descripcion: List<Descripcion> = emptyList(),
    @SerializedName("evolution_chain") val cadenaEvolucion: CadenaEvoluciones = CadenaEvoluciones("")

)

data class Descripcion(
    @SerializedName("flavor_text") val texto: String,
    @SerializedName("language") val idioma: Lenguaje
)

data class Lenguaje(
    @SerializedName("name") val nombre: String,
    @SerializedName("url") val url: String
)

data class CadenaEvoluciones(
    @SerializedName("url") val url: String
)

fun getDescripcionDeLista(lista: List<Descripcion>?): String {

    if (lista.isNullOrEmpty()) return "Descripción no disponible"

    val entrada = lista.find { it.idioma.nombre == "es" }
        ?: lista.find { it.idioma.nombre == "en" }

    return entrada?.texto
        ?.replace("\n", " ")
        ?.replace("\r", " ")
        ?.replace("\u000c", " ")
        ?: "Descripción no disponible"
}