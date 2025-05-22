package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltroNotificaciones (
    var idUsuario: Long,
    override var pagina: Int = 0,
    override var cantidadPorPagina: Int = 4,
    override var tipoFiltro: String = "Notificaciones"
): IFiltro