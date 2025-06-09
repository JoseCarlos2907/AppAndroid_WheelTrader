package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ConnectException
import java.net.Socket

class ConectionViewModel : ViewModel() {
    private var _uiState = MutableStateFlow(ConectionUiState())
    val uiState: StateFlow<ConectionUiState> = _uiState.asStateFlow()

    private var dis: DataInputStream? = null
    private var dos: DataOutputStream? = null

    fun getDataInputStream(): DataInputStream? {
        return dis ?: run {
            dis = DataInputStream(_uiState.value.input)
            dis
        }
    }

    fun getDataOutputStream(): DataOutputStream? {
        return dos ?: run {
            dos = DataOutputStream(_uiState.value.output)
            dos
        }
    }

    fun conectar(address: String, port: Int): Boolean {
        if(_uiState.value.socket == null || _uiState.value.socket!!.isClosed){
            try {
                val socket = Socket(address, port)
                _uiState.value = _uiState.value.copy(
                    socket = socket,
                    input = socket.getInputStream(),
                    output = socket.getOutputStream()
                )
                return true
            } catch (e: ConnectException){
                Log.d("Connection", "Error al conectar con el servidor: " + e.message)
                return false
            }
        }
        return false
    }

    fun cerrarConexion() {
        _uiState.value.socket?.close()

        this.dis = null
        this.dos = null

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

    fun setConfConexionExistente(valor: Boolean){
        _uiState.value = _uiState.value.copy(confConexionExistente = valor)
    }

    fun isConexionActiva(): Boolean {
        return _uiState.value.socket?.isConnected == true && _uiState.value.socket?.isClosed == false
    }
}