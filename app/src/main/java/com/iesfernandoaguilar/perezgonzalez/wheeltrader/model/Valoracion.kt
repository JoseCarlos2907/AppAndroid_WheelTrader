package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Valoracion(
    var idValoracion: Long = -1,
    var valoracion: Int,
    var comentario: String,
    var usuarioEnvia: Usuario,
    var usuarioRecibe: Usuario
)
