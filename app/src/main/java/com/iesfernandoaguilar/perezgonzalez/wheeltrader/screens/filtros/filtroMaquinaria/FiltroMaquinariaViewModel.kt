package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMaquinaria

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroMaquinariaViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroMaquinariaUiState())
    val uiState: StateFlow<FiltroMaquinariaUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Maquinaria(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Maquinaria(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarCiudad_Maquinaria(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Maquinaria(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarAnioMin_Maquinaria(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Maquinaria(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarTipoComb_Maquinaria(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }
}