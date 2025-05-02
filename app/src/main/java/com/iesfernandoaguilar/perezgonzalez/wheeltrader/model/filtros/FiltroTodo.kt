package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroTodo(
    var marca: String?,
    var modelo: String?,
    var anioMinimo: Int?,
    var anioMaximo: Int?,
    var provincia: String?,
    var precioMinimo: Int?,
    var precioMaximo: Int?,
    var tiposVehiculo: List<String>? = listOf("Coche", "Moto", "Camion", "Camioneta", "Maquinaria"),
    var ciudad: String?,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Todo"
)
