package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.databind.ObjectMapper
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.InputStream
import java.io.OutputStream

class AppViewModel(
    private val conectionViewModel: ConectionViewModel
): ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val dis: DataInputStream? by lazy { conectionViewModel.getDataInputStream() }
    private val dos: DataOutputStream? by lazy { conectionViewModel.getDataOutputStream() }

    // private var dis: DataInputStream? = null
    // private var dos: DataOutputStream? = null
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun confFlujos(inputStream: InputStream?, outputStream: OutputStream?, context: Context){
        // this.dis = DataInputStream(inputStream)
        // this.dos = DataOutputStream(outputStream)
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_App(){
        var cierraSesion = false

        var msgRespuesta: Mensaje
        while (!cierraSesion){
            try{
                Log.d("App", "Antes de leer: " + conectionViewModel.uiState.value.socket)
                var linea: String = this.dis?.readUTF()?: "NoInfo"
                Log.d("App", linea)
                var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)
                // Log.d("App", "Aqui no llega")

                var tipo = msgServidor.getTipo()
                // Log.d("App", tipo)
                when(tipo){
                    "ENVIA_ANUNCIOS" -> {
                        Log.d("App", msgServidor.getParams().get(1))

                        var imagenesAnuncios: ArrayList<ByteArray> = ArrayList()
                        var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(2))
                        /*for (i in 0..cantAnuncios){
                            var longitudImg = this.dis?.readInt()?: 0
                            var bytesImg = ByteArray(longitudImg)
                            this.dis?.readFully(bytesImg)
                            imagenesAnuncios.add(bytesImg)
                        }*/

                        // TODO: Guardar los anuncios en algún uiState o algo así para poder pasarlo a la pantalla de la lista de anuncios

                        _uiState.value = _uiState.value.copy(goToListaAnuncios = true)
                    }
                    "" -> {

                    }
                    else -> {
                        Log.d("App", "Raro que entre en el else del when")
                    }
                }
            } catch (e: EOFException) {
                Log.d("App", e.message ?: "Error")
                break;
            }
        }
    }

    fun obtenerAnuncios(filtro: IFiltro, primeraCarga: Boolean){
        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)

        var msg = Mensaje()
        msg.setTipo("OBTENER_ANUNCIOS")
        msg.addParam(filtroJSON)
        msg.addParam(filtro.tipoFiltro)
        msg.addParam(if (primeraCarga) "si" else "no")
        msg.addParam((conectionViewModel.uiState.value.usuario?.idUsuario?: -1L).toString())

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun changeGoToListaAnuncios(value: Boolean){
        _uiState.value = _uiState.value.copy(goToListaAnuncios = value)
    }
}