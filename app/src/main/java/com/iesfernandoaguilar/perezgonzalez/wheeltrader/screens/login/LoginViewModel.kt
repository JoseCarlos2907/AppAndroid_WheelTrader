package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.util.Base64

class LoginViewModel(
    private val conectionViewModel: ConectionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val dis: DataInputStream? by lazy { conectionViewModel.getDataInputStream() }
    private val dos: DataOutputStream? by lazy { conectionViewModel.getDataOutputStream() }

    lateinit var showMsg: ((Context, String) -> Unit)

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var handler: Handler
    var lectorJob: Job? = null

    fun confVM(context: Context) {
        this.context = context
        this.handler = Handler(Looper.getMainLooper())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_Login() {
        if(lectorJob?.isActive == true) return

        lectorJob = viewModelScope.launch(Dispatchers.IO) {
            Log.d("Login", "Arranca hilo")
            var usuarioJSON = ""

            try {
                var msgRespuesta: Mensaje
                while (this.isActive && !_uiState.value.iniciaSesion) {
                    Log.d("Login", "Antes de leer: " + _uiState.value.iniciaSesion)
                    var linea: String = dis?.readUTF() ?: ""
                    Log.d("Login", "Linea: " + linea)
                    var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)

                    var tipo = msgServidor.getTipo()
                    if ("ENVIA_SALT".equals(tipo)) {
                        // Log.d("Login", "ENVIA_SALT")
                        msgRespuesta = Mensaje()
                        msgRespuesta.setTipo("INICIAR_SESION")
                        msgRespuesta.addParam(_uiState.value.currentNombreUsuario)
                        msgRespuesta.addParam(
                            SecureUtils.generate512(
                                _uiState.value.currentContrasenia,
                                Base64.getDecoder().decode(msgServidor.getParams().get(0))
                            )
                        )
                        dos?.writeUTF(Serializador.codificarMensaje(msgRespuesta))
                        dos?.flush()
                    } else if ("INICIA_SESION".equals(tipo)) {
                        Log.d("Login", "INICIA_SESION;" + msgServidor.getParams().get(0))
                        if ("si".equals(msgServidor.getParams().get(0))) {
                            _uiState.value = _uiState.value.copy(iniciaSesion = true)
                            usuarioJSON = msgServidor.getParams().get(1)
                            break;
                        } else if ("no".equals(msgServidor.getParams().get(0))) {
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "Credenciales incorrectas")
                            }
                        } else if ("baneado".equals(msgServidor.getParams().get(0))) {
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "Ese usuario ha sido baneado")
                            }
                        }
                    } else if ("DNI_EXISTE".equals(tipo)) {
                        if ("si".equals(msgServidor.getParams().get(0))) {
                            // Avisar a la interfaz que ya existe un usuario con ese dni
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "El DNI ya ha sido registrado")
                            }
                        } else if ("no".equals(msgServidor.getParams().get(0))) {
                            _uiState.value = _uiState.value.copy(goToPaso2 = true)
                        }

                    } else if ("USUARIO_EXISTE".equals(tipo)) {
                        if ("si".equals(msgServidor.getParams().get(0))) {
                            // Avisar a la interfaz que ya existe un usuario con ese nombre de usuario o correo
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(
                                    context,
                                    "El nombre de usuario o el correo ya ha sido registrado"
                                )
                            }
                        } else if ("no".equals(msgServidor.getParams().get(0))) {
                            _uiState.value = _uiState.value.copy(goToPaso3 = true)
                        }

                    } else if ("USUARIO_REGISTRADO".equals(tipo)) {
                        // Avisar a la interfaz para cambiar de pantalla al paso 4
                        _uiState.value = _uiState.value.copy(goToPaso4 = true)
                    } else if ("CODIGO_ENVIADO".equals(tipo)) {
                        _uiState.value = _uiState.value.copy(goToCodigo = true)
                    } else if ("CORREO_NO_EXISTE".equals(tipo)) {
                        withContext(Dispatchers.Main) {
                            showMsg.invoke(
                                context,
                                "No existe ningún usuario con ese correo"
                            )
                        }
                    } else if ("CODIGO_CORRECTO".equals(tipo)) {
                        _uiState.value = _uiState.value.copy(
                            goToReiniciarContrasenia = true,
                            saltUsuario = msgServidor.getParams().get(0)
                        )
                    } else if ("CODIGO_INCORRECTO".equals(tipo)) {
                        withContext(Dispatchers.Main) {
                            showMsg.invoke(
                                context,
                                "El código no es el correcto"
                            )
                        }
                    } else {
                        Log.d("Login", "Else del when" + linea)
                    }
                }
            } catch (e: CancellationException) {
                Log.d("Login", "Cancelando corrutina de login")
            } catch (e: EOFException) {
                Log.d("Login", e.message ?: "Error")
            }

            if (uiState.value.iniciaSesion) {
                Log.d("Login", usuarioJSON)

                var cadena = String() + usuarioJSON.toString()

                var mapper = jacksonObjectMapper()
                conectionViewModel.iniciarSesion(mapper.readValue(cadena, Usuario::class.java))
                // Log.d("Login", "Logea")
            }
        }
        lectorJob?.start()
        _uiState.value = _uiState.value.copy(iniciaSesion = false)
    }

    fun pararEscuchaServidor_Login() {
        if(!uiState.value.iniciaSesion) return

        lectorJob?.let {
            if (it.isActive) {
                Log.d("Login", "Hilo parado")
                it.cancel()
            }
        }
        lectorJob = null
    }

    fun iniciarSesion(nombre: String) {
        var msg = Mensaje()
        msg.setTipo("OBTENER_SALT")
        msg.addParam(nombre)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun limpiarInicioSesion(){
        _uiState.value = _uiState.value.copy(
            currentNombreUsuario = "",
            currentContrasenia = ""
        )
    }

    fun comprobarDNI(dni: String) {
        var msg = Mensaje()
        msg.setTipo("COMPROBAR_DNI")
        msg.addParam(dni)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun comprobarNombreUsuYCorreo(nombreUsuario: String, correo: String) {
        var msg = Mensaje()
        msg.setTipo("COMPROBAR_NOMUSU_CORREO")
        msg.addParam(nombreUsuario)
        msg.addParam(correo)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registrarUsuario() {
        var salt: ByteArray = SecureUtils.getSalt()
        var contraseniaHasheada: String = SecureUtils.generate512(_uiState.value.contrasenia, salt)

        var usuario = Usuario(
            nombre = _uiState.value.nombre,
            apellidos = _uiState.value.apellidos,
            dni = _uiState.value.dni,
            direccion = _uiState.value.direccion,
            nombreUsuario = _uiState.value.nombreUsuario,
            contrasenia = contraseniaHasheada,
            correo = _uiState.value.correo,
            correoPP = _uiState.value.correoPP,
            salt = Base64.getEncoder().encodeToString(salt)
        )

        var mapper = jacksonObjectMapper()
        var msg = Mensaje()
        msg.setTipo("REGISTRAR_USUARIO")
        msg.addParam(mapper.writeValueAsString(usuario))
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
    }

    fun limpiarRegistro() {
        _uiState.value = _uiState.value.copy(
            nombre = "",
            apellidos = "",
            dni = "",
            direccion = "",

            nombreUsuario = "",
            correo = "",
            correoPP = "",

            contrasenia = "",
            confContra = ""
        )
    }

    fun recuperarContrasenia_Correo(correo: String){
        var msg = Mensaje()
        msg.setTipo("RECUPERAR_CONTRASENIA")
        msg.addParam(correo)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun recuperarContrasenia_Codigo(codigo: String){
        var msg = Mensaje()
        msg.setTipo("INTENTA_CODIGO")
        msg.addParam(codigo)
        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun recuperarContrasenia_Contrasenias(contrasenia: String){
        // TODO: Reiniciar las contraseñas
    }

    // Login
    fun onNombreUsuarioChange(nombreUsuario: String) {
        _uiState.value = _uiState.value.copy(currentNombreUsuario = nombreUsuario)
    }

    fun onContraseniaChange(contrasenia: String) {
        _uiState.value = _uiState.value.copy(currentContrasenia = contrasenia)
    }

    // Registro paso 1
    fun onNombreChange(nombre: String) {
        _uiState.value = _uiState.value.copy(nombre = nombre)
    }

    fun onApellidosChange(apellidos: String) {
        _uiState.value = _uiState.value.copy(apellidos = apellidos)
    }

    fun onDniChange(dni: String) {
        _uiState.value = _uiState.value.copy(dni = dni)
    }

    fun onDireccionChange(direccion: String) {
        _uiState.value = _uiState.value.copy(direccion = direccion)
    }

    fun reiniciarGoToPaso2() {
        _uiState.value = _uiState.value.copy(goToPaso2 = false)
    }

    // Registro paso 2
    fun onNombreUsuarioRegistroChange(nombreUsuario: String) {
        _uiState.value = _uiState.value.copy(nombreUsuario = nombreUsuario)
    }

    fun onCorreoChange(correo: String) {
        _uiState.value = _uiState.value.copy(correo = correo)
    }

    fun onCorreoPPChange(correoPP: String) {
        _uiState.value = _uiState.value.copy(correoPP = correoPP)
    }

    fun reiniciarGoToPaso3() {
        _uiState.value = _uiState.value.copy(goToPaso3 = false)
    }

    // Registro paso 3
    fun onContraseniaRegistroChange(contrasenia: String) {
        _uiState.value = _uiState.value.copy(contrasenia = contrasenia)
    }

    fun onConfContraChange(confContra: String) {
        _uiState.value = _uiState.value.copy(confContra = confContra)
    }

    fun reiniciarGoToPaso4() {
        _uiState.value = _uiState.value.copy(goToPaso4 = false)
    }

    // Recuperar Contraseña
    fun asignarGoToCodigo(goToCodigo: Boolean){
        _uiState.value = _uiState.value.copy(goToCodigo = goToCodigo)
    }

    fun asignarGoToReiniciarContrasenia(goToReiniciarContrasenia: Boolean){
        _uiState.value = _uiState.value.copy(goToReiniciarContrasenia = goToReiniciarContrasenia)
    }

    fun mostrarToast(msg: String) {
        handler.post {
            showMsg.invoke(context, msg)
        }
    }
}

class LoginViewModelFactory(
    private val conectionViewModel: ConectionViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(conectionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}