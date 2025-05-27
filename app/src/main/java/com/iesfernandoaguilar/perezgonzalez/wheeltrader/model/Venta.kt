package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

import java.time.LocalDateTime

data class Venta(
    var idVenta: Long = -1,
    var fechaFinGarantia: String,
    var anuncio: Anuncio? = null,
    var vendedor: Usuario? = null,
    var comprador: Usuario? = null,
    var pagos: List<Pago>? = null
)
