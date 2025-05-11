package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMoto

data class PublicarMotoUiState(
    var marca: String = "",
    var modelo: String = "",
    var cv: String = "",
    var anio: String = "",
    var kilometaje: String = "",
    var cantMarchas: String = "",
    var nBastidor: String = "",
    var matricula: String = "",
    var tipoCombustible: String = "",

    var ciudad: String = "",
    var provincia: String = "",
    var precio: String = "",
    var descripcion: String = "",

    var imagenes: List<ByteArray> = emptyList()
)
