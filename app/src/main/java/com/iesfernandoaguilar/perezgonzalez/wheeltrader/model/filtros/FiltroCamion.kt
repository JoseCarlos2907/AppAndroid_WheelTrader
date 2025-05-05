package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroCamion(
    var marca: String? = null,
    var modelo: String? = null,
    var anioMinimo: Int? = 1950,
    var anioMaximo: Int? = 2025,
    var kmMinimo: Int? = 0,
    var kmMaximo: Int? = 2000000,
    var tipoCombustible: String? = null,
    var cvMinimo: Int? = 0,
    var cvMaximo: Int? = 0,
    var cantMarchas: Int? = 0,
    var provincia: String? = null,
    var ciudad: String? = null,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Camion"
)
