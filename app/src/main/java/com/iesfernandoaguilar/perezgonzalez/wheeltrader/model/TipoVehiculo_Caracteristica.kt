package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class TipoVehiculo_Caracteristica(
    var idTipoVehiculo_Caracteristica: Long = -1,
    var obligatorio: Boolean,
    var tipoVehiculo: TipoVehiculo,
    var caracteristica: Caracteristica
)
