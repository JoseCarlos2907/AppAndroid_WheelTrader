package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta

data class PublicarCamionetaUiState(
    var marca: String = "",
    var modelo: String = "",
    var anio: String = "",
    var cv: String = "",
    var kilometaje: String = "",
    var cantMarchas: String = "",
    var nBastidor: String = "",
    var matricula: String = "",
    var nPuertas: String = "",
    var capacidadCarga: String = "",
    var tipoCombustible: String = "",
    var tipoTraccion: String = "",

    var ciudad: String = "",
    var provincia: String = "",
    var precio: String = "",
    var descripcion: String = "",

    var imagenes: List<ByteArray> = emptyList()
)
