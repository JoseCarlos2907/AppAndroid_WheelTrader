package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
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

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    var lectorApp: Thread? = null

    fun confVM(context: Context){
        this.context = context
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_App(){
        if(lectorApp?.isAlive == true) return

        lectorApp = Thread() {
            var mapper = jacksonObjectMapper()
            var cierraSesion = false

            var msgRespuesta: Mensaje
            while (!cierraSesion){
                try{
                    // Log.d("App", "Antes de leer: " + conectionViewModel.uiState.value.socket)
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
                                Log.d("App", longitudImg.toString())
                                var bytesImg = ByteArray(longitudImg)
                                this.dis?.readFully(bytesImg)
                                imagenesAnuncios.add(bytesImg)
                            }*/

                            // TODO: Guardar los anuncios en algún uiState o algo así para poder pasarlo a la pantalla de la lista de anuncios

                            var anuncios: List<Anuncio> = mapper.readValue(msgServidor.getParams().get(1), object: TypeReference<List<Anuncio>>(){})
                            aniadirAnuncios(anuncios)
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
        lectorApp?.start()
    }

    fun pararEscuchaServidor_App(){
        lectorApp?.interrupt()
        lectorApp = null
    }

    fun obtenerAnuncios(filtro: IFiltro?, primeraCarga: Boolean){
        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)

        var msg = Mensaje()
        msg.setTipo("OBTENER_ANUNCIOS")
        msg.addParam(filtroJSON)
        msg.addParam(filtro?.tipoFiltro?: "Todo")
        msg.addParam(if (primeraCarga) "si" else "no")
        msg.addParam((conectionViewModel.uiState.value.usuario?.idUsuario?: -1L).toString())

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun aniadirAnuncios(anunciosNuevos: List<Anuncio>){
        _uiState.value = _uiState.value.copy(anunciosEncontrados = _uiState.value.anunciosEncontrados + anunciosNuevos)
    }

    fun vaciarAnuncios(){
        _uiState.value = _uiState.value.copy(anunciosEncontrados = ArrayList())
    }
}