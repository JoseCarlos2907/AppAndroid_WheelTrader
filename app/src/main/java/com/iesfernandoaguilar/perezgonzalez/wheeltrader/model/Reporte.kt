package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Reporte(
    var idReporte: Long = -1,
    var motivo: String? = null,
    var explicacion: String? = null,
    var usuarioEnvia: Usuario,
    var usuarioRecibe: Usuario
)
