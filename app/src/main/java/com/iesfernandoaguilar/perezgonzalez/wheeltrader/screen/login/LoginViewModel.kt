package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream
import java.util.Base64

class LoginViewModel(
    private val conectionViewModel: ConectionViewModel
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private var dis: DataInputStream? = null
    private var dos: DataOutputStream? = null

    fun confFlujos(inputStream: InputStream?, outputStream: OutputStream?){
        this.dis = DataInputStream(inputStream)
        this.dos = DataOutputStream(outputStream)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_Login() {
        var iniciaSesion = false

        var usuarioJSON = ""

        var msgRespuesta: Mensaje
        while (!iniciaSesion) {
            try {
                var linea: String = this.dis?.readUTF()?: ""
                var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)

                if ("ENVIA_SALT".equals(msgServidor.getTipo())) {
                    //Log.d("Login","ENVIA_SALT")
                    msgRespuesta = Mensaje()
                    msgRespuesta.setTipo("INICIAR_SESION")
                    msgRespuesta.addParam(_uiState.value.currentNombreUsuario)
                    msgRespuesta.addParam(
                        SecureUtils.generate512(
                            _uiState.value.currentContrasenia,
                            Base64.getDecoder().decode(msgServidor.getParams().get(0))
                        )
                    )
                    this.dos?.writeUTF(Serializador.codificarMensaje(msgRespuesta))
                } else if ("INICIA_SESION".equals(msgServidor.getTipo())) {
                    //Log.d("Login","INICIA_SESION")
                    if ("si".equals(msgServidor.getParams().get(0))) {
                        iniciaSesion = true
                        usuarioJSON = msgServidor.getParams().get(1)
                    } else if ("no".equals(msgServidor.getParams().get(0))) {
                        // TODO: Mostrar que no coincide la contraseña
                    }
                }

            } catch (e: EOFException) {
                Log.d("Login", e.message ?: "Error")
            }
        }

        Log.d("Login", usuarioJSON)

        var mapper = jacksonObjectMapper()
        conectionViewModel.iniciarSesion(mapper.readValue(usuarioJSON, Usuario::class.java))
    }

    fun iniciarSesion(nombre: String){
        var msg = Mensaje()
        msg.setTipo("OBTENER_SALT")
        msg.addParam(nombre)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun prueba() {
        var i = 0
        while (true) {
            Thread.sleep(2000)
            Log.d("Login", "Bucle: " + i++)
        }
    }

    fun onNombreUsuarioChange(nombreUsuario: String){
        _uiState.value = _uiState.value.copy(currentNombreUsuario = nombreUsuario)
    }

    fun onContraseniaChange(contrasenia: String){
        _uiState.value = _uiState.value.copy(currentContrasenia = contrasenia)
    }
}