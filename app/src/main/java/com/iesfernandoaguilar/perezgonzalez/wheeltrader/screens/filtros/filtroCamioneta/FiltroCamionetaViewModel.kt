package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamioneta

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FiltroCamionetaViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(FiltroCamionetaUiState())
    val uiState: StateFlow<FiltroCamionetaUiState> = _uiState.asStateFlow()

    fun cambiarMarca_Camioneta(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Camioneta(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarCantMarchas_Camioneta(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarKmMinimo_Camioneta(kmMinimo: String){
        _uiState.value = _uiState.value.copy(kmMinimo = kmMinimo)
    }

    fun cambiarKmMaximo_Camioneta(kmMaximo: String){
        _uiState.value = _uiState.value.copy(kmMaximo = kmMaximo)
    }

    fun cambiarNPuertas_Camioneta(nPuertas: String){
        _uiState.value = _uiState.value.copy(nPuertas = nPuertas)
    }

    fun cambiarCiudad_Camioneta(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Camioneta(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }
    fun cambiarCvMinimo_Camioneta(cvMinimo: String){
        _uiState.value = _uiState.value.copy(cvMinimo = cvMinimo)
    }

    fun cambiarCvMaximo_Camioneta(cvMaximo: String){
        _uiState.value = _uiState.value.copy(cvMaximo = cvMaximo)
    }

    fun cambiarAnioMin_Camioneta(anioMin: String){
        _uiState.value = _uiState.value.copy(anioMinimo = anioMin)
    }

    fun cambiarAnioMax_Camioneta(anioMax: String){
        _uiState.value = _uiState.value.copy(anioMaximo = anioMax)
    }

    fun cambiarTipoComb_Camioneta(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarTipoTraccion_Camioneta(tipoTraccion: String){
        _uiState.value = _uiState.value.copy(tipoTraccion = tipoTraccion)
    }

    fun cambiarCargaKg_Camionea(cargaKg: String){
        _uiState.value = _uiState.value.copy(cargaKg = cargaKg)
    }
}