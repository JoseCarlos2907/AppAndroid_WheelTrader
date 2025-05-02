package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroCoche(
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
    var nPuertas: Int?,
    var provincia: String?,
    var ciudad: String?,
    var transmision: String?,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Coche"
)
