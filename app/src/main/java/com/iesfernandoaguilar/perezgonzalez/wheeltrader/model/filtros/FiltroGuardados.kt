package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

data class FiltroGuardados(
    var nombreUsuario: String?,
    var pagina: Int = 1,
    var cantidadPorPagina: Int = 10,
    var tipoFiltro: String = "Guardados"
)
