package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Revision(
    var idRevision: Long = -1,
    var imagen: Array<Byte>? = null,
    var asunto: String,
    var descripcion: String,
    var estado: String,
    var reunion: Reunion
)
