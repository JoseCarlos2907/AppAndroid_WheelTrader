package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltrosViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltrosUiState())
    val uiState: StateFlow<FiltrosUiState> = _uiState.asStateFlow()

    fun asignarTipoFiltro(tipo: String){
        _uiState.value = _uiState.value.copy(tipoFiltro = tipo)
    }

    fun asignarFiltro(filtro: IFiltro){
        _uiState.value = _uiState.value.copy(filtro = filtro)
    }
}