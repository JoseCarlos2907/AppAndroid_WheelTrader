package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Notificacion(
    var idNotificacion: Long = -1,
    var titulo: String,
    var mensaje: String,
    var estado: String,
    var tipo: String,
    var usuarioEnvia: Usuario,
    var usuarioRecibe: Usuario
)
