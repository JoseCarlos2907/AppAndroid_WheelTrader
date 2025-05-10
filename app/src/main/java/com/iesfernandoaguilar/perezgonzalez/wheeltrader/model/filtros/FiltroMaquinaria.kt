package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroMaquinaria(
    var marca: String? = null,
    var modelo: String? = null,
    var anioMinimo: Int? = null,
    var anioMaximo: Int? = null,
    var tipoCombustible: String? = null,
    var provincia: String? = null,
    var ciudad: String? = null,
    override var pagina: Int = 0,
    override var cantidadPorPagina: Int = 2,
    override var tipoFiltro: String = "Maquinaria"
): IFiltro
