package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PublicarCamionetaViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicarCamionetaUiState())
    val uiState: StateFlow<PublicarCamionetaUiState> = _uiState.asStateFlow()

    fun aniadirImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() + imagen)
    }

    fun eliminarImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() - imagen)
    }


    fun cambiarMarca_Camioneta(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Camioneta(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnio_Camioneta(anio: String){
        _uiState.value = _uiState.value.copy(anio = anio)
    }

    fun cambiarCv_Camioneta(cv: String){
        _uiState.value = _uiState.value.copy(cv = cv)
    }

    fun cambiarKm_Camioneta(km: String){
        _uiState.value = _uiState.value.copy(kilometaje = km)
    }

    fun cambiarCantMarchas_Camioneta(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarNBastidor_Camioneta(nBastidor: String){
        _uiState.value = _uiState.value.copy(nBastidor = nBastidor)
    }

    fun cambiarMatricula_Camioneta(matricula: String){
        _uiState.value = _uiState.value.copy(matricula = matricula)
    }

    fun cambiarNPuertas_Camioneta(nPuertas: String){
        _uiState.value = _uiState.value.copy(nPuertas = nPuertas)
    }

    fun cambiarCapacidadCarga_Camioneta(capacidadCarga: String){
        _uiState.value = _uiState.value.copy(capacidadCarga = capacidadCarga)
    }

    fun cambiarTipoComb_Camioneta(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarTipoTraccion_Camioneta(tipoTraccion: String){
        _uiState.value = _uiState.value.copy(tipoTraccion = tipoTraccion)
    }

    fun cambiarCiudad_Camioneta(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Camioneta(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarDescripcion_Camioneta(descripcion: String){
        _uiState.value = _uiState.value.copy(descripcion = descripcion)
    }

    fun cambiarPrecio_Camioneta(precio: String){
        _uiState.value = _uiState.value.copy(precio = precio)
    }
}