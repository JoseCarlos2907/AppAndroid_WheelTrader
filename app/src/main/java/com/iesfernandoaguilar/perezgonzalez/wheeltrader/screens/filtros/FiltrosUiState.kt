package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro

data class FiltrosUiState (
    var tipoFiltro: String = "",
    var filtro: IFiltro? = null,
    var cargando: Boolean = false
)
