package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio

data class AppUiState (
    var anunciosEncontrados: List<Anuncio> = ArrayList(),
    var imagenesAnuncios: List<ByteArray> = ArrayList()
)