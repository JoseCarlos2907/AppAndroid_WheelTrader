package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
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

class FiltrosViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FiltrosViewModel::class.java)) {
            return FiltrosViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}