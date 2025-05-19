package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime

data class Anuncio (
    var idAnuncio: Long = -1,
    var fechaPublicacion: LocalDateTime? = null,
    var fechaExpiracion: LocalDateTime? = null,
    var descripcion: String,
    var precio: Double,
    var estado: String = "EN_VENTA",
    var provincia: String,
    var ciudad: String,
    var matricula: String?,
    var numSerieBastidor: String?,
    var vendedor: Usuario? = null,
    var guardado: Boolean = false,
    var tipoVehiculo: String? = null,
    var imagenes: List<Imagen>? = null,
    var venta: Venta? = null,
    var reuniones: List<Reunion>? = null,
    var valoresCaracteristicas: List<ValorCaracteristica>? = null
)
