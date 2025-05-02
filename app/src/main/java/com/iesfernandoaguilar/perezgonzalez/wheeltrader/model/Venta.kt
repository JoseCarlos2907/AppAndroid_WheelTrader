package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

import java.time.LocalDateTime

data class Venta(
    var idVenta: Long = -1,
    var fechaFinGarantia: LocalDateTime,
    var anuncio: Anuncio,
    var vendedor: Usuario,
    var comprador: Usuario,
    var pagos: List<Pago>? = null
)
