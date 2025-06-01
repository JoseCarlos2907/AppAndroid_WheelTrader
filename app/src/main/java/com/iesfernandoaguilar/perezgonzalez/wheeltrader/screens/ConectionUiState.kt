package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens

import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

data class ConectionUiState (
    var socket: Socket? = null,
    var input: InputStream? = null,
    var output: OutputStream? = null,
    var usuario: Usuario? = null,
    var confConexionExistente: Boolean = false
)