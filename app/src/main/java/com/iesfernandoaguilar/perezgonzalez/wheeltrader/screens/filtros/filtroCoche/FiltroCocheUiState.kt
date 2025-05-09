package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche

data class FiltroCocheUiState(
    var marca: String = "",
    var modelo: String = "",
    var cantMarchas: String = "",
    var kmMinimo: String = "",
    var kmMaximo: String = "",
    var nPuertas: String = "",
    var ciudad: String = "",
    var provincia: String = "",
    var cvMinimo: String = "",
    var cvMaximo: String = "",
    var anioMinimo: String = "",
    var anioMaximo: String = "",
    var tipoCombustible: String = "",
    var transmision: String = ""
)
