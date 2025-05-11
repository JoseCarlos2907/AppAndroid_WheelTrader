package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamion

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.PublicarCamionetaUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PublicarCamionViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicarCamionUiState())
    val uiState: StateFlow<PublicarCamionUiState> = _uiState.asStateFlow()

    fun aniadirImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() + imagen)
    }

    fun eliminarImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() - imagen)
    }


    fun cambiarMarca_Camion(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Camion(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnio_Camion(anio: String){
        _uiState.value = _uiState.value.copy(anio = anio)
    }

    fun cambiarCv_Camion(cv: String){
        _uiState.value = _uiState.value.copy(cv = cv)
    }

    fun cambiarKm_Camion(km: String){
        _uiState.value = _uiState.value.copy(kilometaje = km)
    }

    fun cambiarCantMarchas_Camion(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarNBastidor_Camion(nBastidor: String){
        _uiState.value = _uiState.value.copy(nBastidor = nBastidor)
    }

    fun cambiarMatricula_Camion(matricula: String){
        _uiState.value = _uiState.value.copy(matricula = matricula)
    }

    fun cambiarCapacidadCarga_Camion(capacidadCarga: String){
        _uiState.value = _uiState.value.copy(capacidadCarga = capacidadCarga)
    }

    fun cambiarTipoComb_Camion(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarCiudad_Camion(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Camion(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarDescripcion_Camion(descripcion: String){
        _uiState.value = _uiState.value.copy(descripcion = descripcion)
    }

    fun cambiarPrecio_Camion(precio: String){
        _uiState.value = _uiState.value.copy(precio = precio)
    }
}