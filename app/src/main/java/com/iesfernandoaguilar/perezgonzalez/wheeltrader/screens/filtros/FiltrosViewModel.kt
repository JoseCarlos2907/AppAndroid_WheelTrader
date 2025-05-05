package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltrosViewModel() {
    private val _uiState = MutableStateFlow(FiltrosUiState())
    val uiState: StateFlow<FiltrosUiState> = _uiState.asStateFlow()

    fun asignarTipoFiltro(tipo: String){
        _uiState.value = _uiState.value.copy(tipoFiltro = tipo)
    }

    fun asignarFiltroTodo(filtro: IFiltro){
        _uiState.value = _uiState.value.copy(filtroTodo = filtro)
    }
}