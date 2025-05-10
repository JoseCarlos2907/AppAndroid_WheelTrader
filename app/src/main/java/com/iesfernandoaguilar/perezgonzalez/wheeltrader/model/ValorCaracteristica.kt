package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class ValorCaracteristica(
    var idValorCaracteristica: Long = -1,
    var valor: String,
    var anuncio: Anuncio? = null,
    var nombreCaracteristica: String
)
