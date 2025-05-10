package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroCamionViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroCamionUiState())
    val uiState: StateFlow<FiltroCamionUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Camion(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Camion(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarCargaKg_Camion(cargaKg: String){
        _uiState.value = _uiState.value.copy(cargaKg = cargaKg)
    }

    fun cambiarKmMinimo_Camion(kmMinimo: String){
        _uiState.value = _uiState.value.copy(kmMinimo = kmMinimo)
    }

    fun cambiarKmMaximo_Camion(kmMaximo: String){
        _uiState.value = _uiState.value.copy(kmMaximo = kmMaximo)
    }

    fun cambiarCiudad_Camion(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Camion(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }
    fun cambiarCvMinimo_Camion(cvMinimo: String){
        _uiState.value = _uiState.value.copy(cvMinimo = cvMinimo)
    }

    fun cambiarCvMaximo_Camion(cvMaximo: String){
        _uiState.value = _uiState.value.copy(cvMaximo = cvMaximo)
    }

    fun cambiarAnioMin_Camion(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Camion(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarTipoComb_Camion(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }
}