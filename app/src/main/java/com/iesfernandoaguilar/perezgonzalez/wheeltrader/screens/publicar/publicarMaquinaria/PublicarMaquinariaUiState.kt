package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMaquinaria

data class PublicarMaquinariaUiState(
    var marca: String = "",
    var modelo: String = "",
    var anio: String = "",
    var nSerieBastidor: String = "",
    var tipoCombustible: String = "",

    var ciudad: String = "",
    var provincia: String = "",
    var precio: String = "",
    var descripcion: String = "",

    var imagenes: List<ByteArray> = emptyList()
)
