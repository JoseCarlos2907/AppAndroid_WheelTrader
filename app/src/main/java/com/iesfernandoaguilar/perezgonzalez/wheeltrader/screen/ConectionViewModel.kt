package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutput
import java.io.DataOutputStream
import java.io.EOFException
import java.net.Socket
import java.util.Base64

class ConectionViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(ConectionUiState())
    val uiState: StateFlow<ConectionUiState> = _uiState.asStateFlow()

    fun conectar(address: String, port: Int) {
        val socket = Socket(address, port)
        _uiState.value = _uiState.value.copy(
            socket = socket,
            input = socket.getInputStream(),
            output = socket.getOutputStream()
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