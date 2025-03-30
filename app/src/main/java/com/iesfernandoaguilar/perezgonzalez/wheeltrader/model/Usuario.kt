package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Usuario(
    var idUsuario: Long,
    var nombre: String,
    var apellidos: String,
    var dni: String,
    var direccion: String,
    var nombreUsuario: String,
    var contrasenia: String,
    var correo: String,
    var correoPP: String,
    var rol: String,
    var estado: String,
    var salt: String
)
