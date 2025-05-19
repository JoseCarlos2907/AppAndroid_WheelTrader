package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Notificacion(
    var idNotificacion: Long = -1,
    var titulo: String? = null,
    var mensaje: String? = null,
    var estado: String? = null,
    var tipo: String? = null,
    var usuarioEnvia: Usuario? = null,
    var usuarioRecibe: Usuario? = null,
    var anuncio: Anuncio? = null
)
