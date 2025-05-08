package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import java.time.LocalDateTime

data class AppUiState (
    //var anunciosEncontrados: List<Anuncio> = listOf(Anuncio(provincia = "pr", ciudad = "ciu", estado = "est", precio = 0.01, guardado = true, descripcion = "desc", tipoVehiculo = "tipo", fechaPublicacion = null, fechaExpiracion = null, numSerieBastidor = "serie", matricula = "mat", vendedor = null, venta = null)),
    var anunciosEncontrados: List<Anuncio> = emptyList(),
    var imagenesAnuncios: List<ByteArray> = emptyList(),
    var cargando: Boolean = false,
    val noHayMasAnuncios: Boolean = false
)