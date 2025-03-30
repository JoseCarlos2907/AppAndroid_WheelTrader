package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    lateinit var onError: ((Context, String) -> Unit)
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun confFlujos(inputStream: InputStream?, outputStream: OutputStream?, context: Context){
        this.dis = DataInputStream(inputStream)
        this.dos = DataOutputStream(outputStream)
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_Login() {
        var iniciaSesion = false

        var usuarioJSON = ""

        var msgRespuesta: Mensaje

        val handler = Handler(Looper.getMainLooper())
        while (!iniciaSesion) {
            try {
                var linea: String = this.dis?.readUTF()?: ""
                var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)

                var tipo = msgServidor.getTipo()
                if("ENVIA_SALT".equals(tipo)){
                    Log.d("Login","ENVIA_SALT")
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
                }else if("INICIA_SESION".equals(tipo)){
                    Log.d("Login","INICIA_SESION;"+msgServidor.getParams().get(0))
                    if ("si".equals(msgServidor.getParams().get(0))) {
                        iniciaSesion = true
                        usuarioJSON = msgServidor.getParams().get(1)
                    } else if ("no".equals(msgServidor.getParams().get(0))) {
                        handler.post{
                            onError.invoke(context, "Credenciales incorrectas")
                        }
                    }
                }else if("DNI_EXISTE".equals(tipo)){
                    // Avisar a la interfaz para cambiar de pantalla al paso 2

                }else if("USUARIO_EXISTE".equals(tipo)){
                    // Avisar a la interfaz para cambiar de pantalla al paso 3

                }else if("USUARIO_REGISTRADO".equals(tipo)){
                    // Avisar a la interfaz para cambiar de pantalla al paso 4

                }
            } catch (e: EOFException) {
                Log.d("Login", e.message ?: "Error")
                break;
            }
        }

        Log.d("Login", usuarioJSON)

        var cadena = String() + usuarioJSON.toString()

        var mapper = jacksonObjectMapper()
        conectionViewModel.iniciarSesion(mapper.readValue(cadena, Usuario::class.java))
    }

    fun iniciarSesion(nombre: String){
        var msg = Mensaje()
        msg.setTipo("OBTENER_SALT")
        msg.addParam(nombre)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun registroPaso1(dni: String){
        var msg = Mensaje()
        msg.setTipo("COMPROBAR_DNI")
        msg.addParam(dni)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun onRegistroPaso1Change(
        nombre: String,
        apellidos: String,
        dni: String,
        direccion: String
    ){
        var us = _uiState.value.usuarioRegistro
        us?.nombre = nombre
        us?.apellidos = apellidos
        us?.dni = dni
        us?.direccion = direccion

        _uiState.value.copy(usuarioRegistro = us)
    }

    fun onNombreUsuarioChange(nombreUsuario: String){
        _uiState.value = _uiState.value.copy(currentNombreUsuario = nombreUsuario)
    }

    fun onContraseniaChange(contrasenia: String){
        _uiState.value = _uiState.value.copy(currentContrasenia = contrasenia)
    }
}