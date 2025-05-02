package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Caracteristica(
    var idCaracteristica: Long = -1,
    var nombre: String,
    var tipo_dato: String,
    var valorMax: Int,
    var valorMin: Int,
    var opciones: String,
    var tiposVehiculoCaracteristica: List<TipoVehiculo_Caracteristica>? = null,
    var valoresCaracteristicas: List<ValorCaracteristica>? = null
)
