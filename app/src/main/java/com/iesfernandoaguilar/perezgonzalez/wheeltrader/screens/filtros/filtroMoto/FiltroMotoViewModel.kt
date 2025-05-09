package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMoto

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroMotoViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroMotoUiState())
    val uiState: StateFlow<FiltroMotoUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Moto(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Moto(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarCantMarchas_Moto(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarKmMinimo_Moto(kmMinimo: String){
        _uiState.value = _uiState.value.copy(kmMinimo = kmMinimo)
    }

    fun cambiarKmMaximo_Moto(kmMaximo: String){
        _uiState.value = _uiState.value.copy(kmMaximo = kmMaximo)
    }

    fun cambiarCiudad_Moto(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Moto(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }
    fun cambiarCvMinimo_Moto(cvMinimo: String){
        _uiState.value = _uiState.value.copy(cvMinimo = cvMinimo)
    }

    fun cambiarCvMaximo_Moto(cvMaximo: String){
        _uiState.value = _uiState.value.copy(cvMaximo = cvMaximo)
    }

    fun cambiarAnioMin_Moto(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Moto(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarTipoComb_Moto(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }
}