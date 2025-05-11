package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMaquinaria

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PublicarMaquinariaViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicarMaquinariaUiState())
    val uiState: StateFlow<PublicarMaquinariaUiState> = _uiState.asStateFlow()

    fun aniadirImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() + imagen)
    }

    fun eliminarImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() - imagen)
    }

    fun cambiarMarca_Maquinaria(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Maquinaria(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnio_Maquinaria(anio: String){
        _uiState.value = _uiState.value.copy(anio = anio)
    }

    fun cambiarTipoComb_Maquinaria(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarCiudad_Maquinaria(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Maquinaria(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarDescripcion_Maquinaria(descripcion: String){
        _uiState.value = _uiState.value.copy(descripcion = descripcion)
    }

    fun cambiarPrecio_Maquinaria(precio: String){
        _uiState.value = _uiState.value.copy(precio = precio)
    }

    fun cambiarNSerieBastidor_Maquinaria(nSerieBastidor: String){
        _uiState.value = _uiState.value.copy(nSerieBastidor = nSerieBastidor)
    }
}