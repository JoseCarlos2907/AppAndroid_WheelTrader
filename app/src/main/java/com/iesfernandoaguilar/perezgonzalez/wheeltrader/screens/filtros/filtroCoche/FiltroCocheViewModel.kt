package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodoUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroCocheViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroCocheUiState())
    val uiState: StateFlow<FiltroCocheUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Coche(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Coche(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarCantMarchas_Coche(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarKmMinimo_Coche(kmMinimo: String){
        _uiState.value = _uiState.value.copy(kmMinimo = kmMinimo)
    }

    fun cambiarKmMaximo_Coche(kmMaximo: String){
        _uiState.value = _uiState.value.copy(kmMaximo = kmMaximo)
    }

    fun cambiarNPuertas_Coche(nPuertas: String){
        _uiState.value = _uiState.value.copy(nPuertas = nPuertas)
    }

    fun cambiarCiudad_Coche(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Coche(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }
    fun cambiarCvMinimo_Coche(cvMinimo: String){
        _uiState.value = _uiState.value.copy(cvMinimo = cvMinimo)
    }

    fun cambiarCvMaximo_Coche(cvMaximo: String){
        _uiState.value = _uiState.value.copy(cvMaximo = cvMaximo)
    }

    fun cambiarAnioMin_Coche(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Coche(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarTipoComb_Coche(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarTransmision_Coche(transmision: String){
        _uiState.value = _uiState.value.copy(transmision = transmision)
    }
}
