package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login;

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario

data class LoginUiState (
    // Login
    var currentNombreUsuario: String = "",
    var currentContrasenia: String = "",
    var iniciaSesion: Boolean = false,

    // Registro paso 1
    var nombre: String = "",
    var apellidos: String = "",
    var dni: String = "",
    var direccion: String = "",
    var goToPaso2: Boolean = false,

    // Registro paso 2
    var nombreUsuario: String = "",
    var correo: String = "",
    var correoPP: String = "",
    var goToPaso3: Boolean = false,

    // Registro paso 3
    var contrasenia: String = "",
    var confContra: String = "",
    var goToPaso4: Boolean = false
)
