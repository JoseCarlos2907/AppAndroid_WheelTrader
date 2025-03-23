package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

data class ConectionUiState (
    var socket: Socket? = null,
    var input: InputStream? = null,
    var output: OutputStream? = null
)