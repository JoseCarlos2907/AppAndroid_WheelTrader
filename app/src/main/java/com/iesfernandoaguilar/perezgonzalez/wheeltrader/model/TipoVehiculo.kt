package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class TipoVehiculo(
    var idTipoVehiculo: Long = -1,
    var tipo: String,
    var anuncios: List<Anuncio>? = null,
    var tiposvehiculoCaracteristicas: List<TipoVehiculo_Caracteristica>? = null
)
