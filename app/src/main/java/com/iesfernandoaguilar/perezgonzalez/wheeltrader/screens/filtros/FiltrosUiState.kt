package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo

data class FiltrosUiState (
    var tipoFiltro: String = "",
    var filtroTodo: IFiltro? = null,
)
