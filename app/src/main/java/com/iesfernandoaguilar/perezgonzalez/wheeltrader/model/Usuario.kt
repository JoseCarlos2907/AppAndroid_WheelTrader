package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Usuario(
    var idUsuario: Long = -1,
    var nombre: String,
    var apellidos: String,
    var dni: String,
    var direccion: String,
    var nombreUsuario: String,
    var contrasenia: String,
    var correo: String,
    var correoPP: String,
    var rol: String = "USUARIO",
    var estado: String = "ACTIVO",
    var salt: String,
    var reportesEnviados: List<Reporte>? = null,
    var reportesRecibidos: List<Reporte>? = null,
    var notificacionesEnviadas: List<Notificacion>? = null,
    var notificacionesRecibidas: List<Notificacion>? = null,
    var valoracionesEnviadas: List<Valoracion>? = null,
    var valoracionesRecibidas: List<Valoracion>? = null,
    var anunciosGuardados: Set<Anuncio>? = null,
    var anunciosPublicados: List<Anuncio>? = null,
    var ventas: List<Venta>? = null,
    var compras: List<Venta>? = null,
    var reunionesRecibidas: List<Reunion>? = null,
    var reunionesOfrecidas: List<Reunion>? = null
)
