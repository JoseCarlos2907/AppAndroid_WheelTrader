package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Cuestionario(
    var idCuestionario: Long = -1,
    var comodidad: Int,
    var rendimiento: Int,
    var ciudado: Int,
    var valoracionGeneral: Int,
    var reunion: Reunion
)
