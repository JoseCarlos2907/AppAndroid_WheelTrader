package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroMaquinaria(
    var marca: String?,
    var modelo: String?,
    var anioMinimo: Int?,
    var anioMaximo: Int?,
    var tipoCombustible: String?,
    var provincia: String?,
    var ciudad: String?,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Maquinaria"
)
