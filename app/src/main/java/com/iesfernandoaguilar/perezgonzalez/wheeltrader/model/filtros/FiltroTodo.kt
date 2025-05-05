package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroTodo(
    var marca: String? = null,
    var modelo: String? = null,
    var anioMinimo: Int? = null,
    var anioMaximo: Int? = null,
    var precioMinimo: Double? = null,
    var precioMaximo: Double? = null,
    var tiposVehiculo: List<String> = listOf("Coche", "Moto", "Camion", "Camioneta", "Maquinaria"),
    var ciudad: String? = null,
    var provincia: String? = null,
    var pagina: Int = 0,
    var cantidadPorPagina: Int = 10,
    override var tipoFiltro: String = "Todo"
): IFiltro