package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Imagen(
    var idImagen: Long,
    var imagen: Array<Byte>? = null,
    var anuncio: Anuncio
)
