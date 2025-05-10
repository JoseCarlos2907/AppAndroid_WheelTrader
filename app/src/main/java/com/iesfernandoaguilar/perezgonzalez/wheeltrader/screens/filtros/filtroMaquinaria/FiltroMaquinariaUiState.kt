package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMaquinaria

data class FiltroMaquinariaUiState(
    var marca: String = "",
    var modelo: String = "",
    var ciudad: String = "",
    var provincia: String = "",
    var anioMinimo: String = "",
    var anioMaximo: String = "",
    var tipoCombustible: String = "",
)
