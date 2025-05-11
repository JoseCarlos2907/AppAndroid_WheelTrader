package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamion

data class PublicarCamionUiState(
    var marca: String = "",
    var modelo: String = "",
    var anio: String = "",
    var cv: String = "",
    var kilometaje: String = "",
    var cantMarchas: String = "",
    var nBastidor: String = "",
    var matricula: String = "",
    var capacidadCarga: String = "",
    var tipoCombustible: String = "",

    var ciudad: String = "",
    var provincia: String = "",
    var precio: String = "",
    var descripcion: String = "",

    var imagenes: List<ByteArray> = emptyList()
)
