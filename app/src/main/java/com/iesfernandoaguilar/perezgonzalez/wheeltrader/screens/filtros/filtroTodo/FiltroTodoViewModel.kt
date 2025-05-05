package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo

import android.util.Log
import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroTodoViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroTodoUiState())
    val uiState: StateFlow<FiltroTodoUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Todo(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Todo(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnioMin_Todo(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Todo(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarPrecioMin_Todo(precioMin: String){
        _uiState.value = _uiState.value.copy(precioMinimo = precioMin)
    }

    fun cambiarPrecioMax_Todo(precioMax: String){
        _uiState.value = _uiState.value.copy(precioMaximo = precioMax)
    }

    fun cambiarCiudad_Todo(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Todo(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

}