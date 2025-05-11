package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio

data class FiltrosUiState (
    var tipoFiltro: String = "",
    var filtro: IFiltro? = null,
    var cargando: Boolean = false
)
