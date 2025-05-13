package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroBarraBusqueda(
    var cadena: String? = null,
    var anioMinimo: Int? = null,
    var anioMaximo: Int? = null,
    var precioMinimo: Double? = null,
    var precioMaximo: Double? = null,
    var tiposVehiculo: ArrayList<String> = ArrayList(),
    var ciudad: String? = null,
    var provincia: String? = null,
    override var pagina: Int = 0,
    override var cantidadPorPagina: Int = 2,
    override var tipoFiltro: String = "BarraBusqueda"
): IFiltro

