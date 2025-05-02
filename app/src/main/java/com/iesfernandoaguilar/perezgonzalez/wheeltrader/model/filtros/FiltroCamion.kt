package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroCamion(
    var marca: String?,
    var modelo: String?,
    var anioMinimo: Int?,
    var anioMaximo: Int?,
    var kmMinimo: Int?,
    var kmMaximo: Int?,
    var tipoCombustible: String?,
    var cvMinimo: Int?,
    var cvMaximo: Int?,
    var cantMarchas: Int?,
    var provincia: String?,
    var ciudad: String?,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Camion"
)
