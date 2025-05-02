package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

import java.time.LocalDateTime

data class Pago(
    var idPago: Long = -1,
    var cantidad: Double,
    var fechaPago: LocalDateTime,
    var venta: Venta
)
