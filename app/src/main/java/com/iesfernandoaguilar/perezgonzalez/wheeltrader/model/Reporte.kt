package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Reporte(
    var idReporte: Long = -1,
    var motivo: String,
    var explicacion: String,
    var usuarioEnvia: Usuario,
    var usuarioRecibe: Usuario
)
