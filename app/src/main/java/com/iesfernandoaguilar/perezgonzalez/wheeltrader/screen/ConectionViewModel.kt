package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
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

class ConectionViewModel: ViewModel() {
    private var _uiState = MutableStateFlow(ConectionUiState())
    val uiState: StateFlow<ConectionUiState> = _uiState.asStateFlow()

    fun conectar(address: String, port: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val socket = Socket(address, port)
            _uiState.value = _uiState.value.copy(
                socket = socket,
                input = socket.getInputStream(),
                output = socket.getOutputStream()
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_Login(nombreUsuario: String, contrasenia: String){
        var dis = DataInputStream(_uiState.value.input)
        var dos = DataOutputStream(_uiState.value.output)
        var iniciaSesion = false

        var usuarioJSON: String = ""

        var msgRespuesta = Mensaje()
        while (!iniciaSesion){
            try {
                var linea: String = dis.readUTF()
                var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)

                if ("ENVIA_SALT".equals(msgServidor.getTipo())){
                    // TODO: Enviar la contaseña del usuario hasheada con el salt
                    msgRespuesta = Mensaje()
                    msgRespuesta.setTipo("INICIAR_SESION")
                    msgRespuesta.addParam(nombreUsuario)
                    msgRespuesta.addParam(SecureUtils.generate512(contrasenia, Base64.getDecoder().decode(msgServidor.getParams().get(0))))
                    dos.writeUTF(Serializador.codificarMensaje(msgRespuesta))
                }else if("INICIA_SESION".equals(msgServidor.getTipo())){
                    if("si".equals(msgServidor.getParams().get(0))){
                        iniciaSesion = true
                        usuarioJSON = msgServidor.getParams().get(1)
                    }else if("no".equals(msgServidor.getParams().get(0))){
                        // TODO: Mostrar que no coincide la contraseña
                    }
                }

            } catch (e: EOFException){
                Log.d("Login", e.message?: "Error")
            }
        }

        // TODO: Guardar el usuario en la sesión y redirigir al usuario a la pantalla de bienvenido
        Log.d("Login", usuarioJSON)
    }

    fun cerrarConexion(){
        _uiState.value = _uiState.value.copy(
            socket = null,
            input = null,
            output = null
        )
    }

}