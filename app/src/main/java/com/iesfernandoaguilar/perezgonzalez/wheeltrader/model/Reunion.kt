package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

import java.time.LocalDateTime

data class Reunion(
    var idReunion: Long = -1,
    var calle: String,
    var fecha: LocalDateTime,
    var estado: String,
    var anuncio: Anuncio,
    var vendedor: Usuario,
    var comprador: Usuario,
    var revisiones: List<Revision>? = null,
    var cuestionario: Cuestionario
)
