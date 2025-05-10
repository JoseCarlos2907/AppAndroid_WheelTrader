package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion

data class FiltroCamionUiState(
    var marca: String = "",
    var modelo: String = "",
    var kmMinimo: String = "",
    var kmMaximo: String = "",
    var ciudad: String = "",
    var provincia: String = "",
    var cvMinimo: String = "",
    var cvMaximo: String = "",
    var anioMinimo: String = "",
    var anioMaximo: String = "",
    var cargaKg: String = "",
    var tipoCombustible: String = "",
)
