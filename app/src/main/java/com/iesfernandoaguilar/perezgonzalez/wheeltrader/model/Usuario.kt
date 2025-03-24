package com.iesfernandoaguilar.perezgonzalez.wheeltrader.model

data class Usuario(
    val id: Long,
    val nombre: String,
    val apellidos: String,
    val dni: String,
    val nombreUsuario: String,
    val contrasenia: String,
    val correo: String,
    val correoPP: String,
    val rol: String,
    val estado: String,
    val salt: String
)
