package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Imagen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCocheUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class PublicarCocheViewModel(): ViewModel() {
    private val _uiState = MutableStateFlow(PublicarCocheUiState())
    val uiState: StateFlow<PublicarCocheUiState> = _uiState.asStateFlow()

    fun aniadirImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() + imagen)
    }

    fun eliminarImagen(imagen: ByteArray){
        _uiState.value = _uiState.value.copy(imagenes = _uiState.value.imagenes.toList() - imagen)
    }


    fun cambiarMarca_Coche(marca: String){
        _uiState.value = _uiState.value.copy(marca = marca)
    }

    fun cambiarModelo_Coche(modelo: String){
        _uiState.value = _uiState.value.copy(modelo = modelo)
    }

    fun cambiarAnio_Coche(anio: String){
        _uiState.value = _uiState.value.copy(anio = anio)
    }

    fun cambiarCv_Coche(cv: String){
        _uiState.value = _uiState.value.copy(cv = cv)
    }

    fun cambiarKm_Coche(km: String){
        _uiState.value = _uiState.value.copy(kilometaje = km)
    }

    fun cambiarCantMarchas_Coche(cantMarchas: String){
        _uiState.value = _uiState.value.copy(cantMarchas = cantMarchas)
    }

    fun cambiarNBastidor_Coche(nBastidor: String){
        _uiState.value = _uiState.value.copy(nBastidor = nBastidor)
    }

    fun cambiarMatricula_Coche(matricula: String){
        _uiState.value = _uiState.value.copy(matricula = matricula)
    }

    fun cambiarNPuertas_Coche(nPuertas: String){
        _uiState.value = _uiState.value.copy(nPuertas = nPuertas)
    }

    fun cambiarTipoComb_Coche(tipoCombustible: String){
        _uiState.value = _uiState.value.copy(tipoCombustible = tipoCombustible)
    }

    fun cambiarTransmision_Coche(transmision: String){
        _uiState.value = _uiState.value.copy(transmision = transmision)
    }

    fun cambiarCiudad_Coche(ciudad: String){
        _uiState.value = _uiState.value.copy(ciudad = ciudad)
    }

    fun cambiarProvincia_Coche(provincia: String){
        _uiState.value = _uiState.value.copy(provincia = provincia)
    }

    fun cambiarDescripcion_Coche(descripcion: String){
        _uiState.value = _uiState.value.copy(descripcion = descripcion)
    }

    fun cambiarPrecio_Coche(precio: String){
        _uiState.value = _uiState.value.copy(precio = precio)
    }
}