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
    var salt: String
)
