package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Imagen

data class PublicarCocheUiState(
    var marca: String = "",
    var modelo: String = "",
    var anio: String = "",
    var cv: String = "",
    var kilometaje: String = "",
    var cantMarchas: String = "",
    var nBastidor: String = "",
    var matricula: String = "",
    var nPuertas: String = "",
    var tipoCombustible: String = "",
    var transmision: String = "",
    var ciudad: String = "",
    var provincia: String = "",
    var precio: String = "",
    var descripcion: String = "",
    var imagenes: List<ByteArray> = emptyList()
)
