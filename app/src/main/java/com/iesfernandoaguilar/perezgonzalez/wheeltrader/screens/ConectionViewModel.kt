package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens

import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.net.Socket

class ConectionViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(ConectionUiState())
    val uiState: StateFlow<ConectionUiState> = _uiState.asStateFlow()

    fun conectar(address: String, port: Int) {
        val socket = Socket(address, port)
        _uiState.value = _uiState.value.copy(
            socket = socket,
            input = socket.getInputStream(),
            output = socket.getOutputStream(),
            usuario = null
        )
    }

    fun cerrarConexion() {
        _uiState.value = _uiState.value.copy(
            socket = null,
            input = null,
            output = null
        )
    }

    fun iniciarSesion(usu: Usuario){
        _uiState.value = _uiState.value.copy(usuario = usu)
    }

    fun cerrarSesion(){
        _uiState.value = _uiState.value.copy(usuario = null)
    }

    fun existeSesion(): Boolean{
        return uiState.value.usuario != null
    }

}