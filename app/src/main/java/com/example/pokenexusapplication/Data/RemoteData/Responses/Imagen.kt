package com.example.pokenexusapplication.Data.RemoteData.Responses

import com.google.gson.annotations.SerializedName

data class Imagen(
    @SerializedName("other") val other: Foto? = null
)
data class Foto(
    @SerializedName("official-artwork") val officialArtwork: urlFoto? =null
)
data class urlFoto(
    @SerializedName("front_default") val frontDefault: String? = null
)

