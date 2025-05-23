package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Notificacion
import java.time.LocalDateTime

data class AppUiState (
    //var anunciosEncontrados: List<Anuncio> = listOf(Anuncio(provincia = "pr", ciudad = "ciu", estado = "est", precio = 0.01, guardado = true, descripcion = "desc", tipoVehiculo = "tipo", fechaPublicacion = null, fechaExpiracion = null, numSerieBastidor = "serie", matricula = "mat", vendedor = null, venta = null)),
    var anunciosEncontrados: List<Anuncio> = emptyList(),
    var imagenesAnuncios: List<ByteArray> = emptyList(),
    var cargando: Boolean = false,
    val noHayMasAnuncios: Boolean = false,
    var goToHome: Boolean = false,
    var anuncioSeleccionado: Anuncio? = null,
    var imagenesAnuncioSeleccionado: List<ByteArray> = emptyList(),
    var goToDetalle: Boolean = false,
    var notificacionesEncontrados: List<Notificacion> = emptyList(),
    var noHayMasNotificaciones: Boolean = false,
    var goToCompraComprador: Boolean = false,
    var goToCompraVendedor: Boolean = false,

    var idNotificacionSeleccionada: Long? = null,
    var idAnuncioNotificacionSeleccionada: Long? = null,
    var idCompradorNotificacionSeleccionada: Long? = null,
    var precioAnuncioNotificacionSeleccionada: Double? = null,

    var goToPayPalScreen: Boolean = false,
    var confirmaPago: Boolean = false,
    var urlPayPal: String? = "https://google.com"
)