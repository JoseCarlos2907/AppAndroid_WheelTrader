package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroCompras(
    var nombreUsuario: String,
    override var pagina: Int = 0,
    override var cantidadPorPagina: Int = 4,
    override var tipoFiltro: String = "Compras"
): IFiltro
