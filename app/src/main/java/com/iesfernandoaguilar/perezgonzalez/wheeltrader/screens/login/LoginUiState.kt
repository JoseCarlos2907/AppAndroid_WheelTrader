package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login;

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario

data class LoginUiState (
    var currentNombreUsuario: String = "",
    var currentContrasenia: String = "",
    var usuarioRegistro: Usuario? = null
)
