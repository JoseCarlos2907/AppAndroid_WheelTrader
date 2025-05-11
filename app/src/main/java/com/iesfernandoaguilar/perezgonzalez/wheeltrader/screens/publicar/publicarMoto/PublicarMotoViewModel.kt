package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMoto

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PublicarMotoViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicarMotoUiState())
    val uiState: StateFlow<PublicarMotoUiState> = _uiState.asStateFlow()

    fun aniadirImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() + imagen)
    }

    fun eliminarImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() - imagen)
    }


    fun cambiarMarca_Moto(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Moto(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnio_Moto(anio: String){
        _uiState.value = _uiState.value.copy(anio = anio)
    }

    fun cambiarCv_Moto(cv: String){
        _uiState.value = _uiState.value.copy(cv = cv)
    }

    fun cambiarKm_Moto(km: String){
        _uiState.value = _uiState.value.copy(kilometaje = km)
    }

    fun cambiarCantMarchas_Moto(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarNBastidor_Moto(nBastidor: String){
        _uiState.value = _uiState.value.copy(nBastidor = nBastidor)
    }

    fun cambiarMatricula_Moto(matricula: String){
        _uiState.value = _uiState.value.copy(matricula = matricula)
    }

    fun cambiarTipoComb_Moto(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarCiudad_Moto(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Moto(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarDescripcion_Moto(descripcion: String){
        _uiState.value = _uiState.value.copy(descripcion = descripcion)
    }

    fun cambiarPrecio_Moto(precio: String){
        _uiState.value = _uiState.value.copy(precio = precio)
    }
}