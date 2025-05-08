package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    lateinit var showMsg: ((Context, String) -> Unit)
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var handler: Handler
    var lectorApp: Thread? = null

    fun confVM(context: Context){
        this.context = context
    }

    fun escucharDelServidor_App(){
        if(lectorApp?.isAlive == true) return

        lectorApp = Thread() {
            var mapper = jacksonObjectMapper()
            var cierraSesion = false

            this.handler = Handler(Looper.getMainLooper())
            while (!cierraSesion){
                try{
                    var linea: String = this.dis?.readUTF()?: "NoInfo"
                    Log.d("App", linea)
                    var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)
                    // Log.d("App", "Aqui no llega")

                    var tipo = msgServidor.getTipo()
                    // Log.d("App", tipo)
                    when(tipo){
                        "ENVIA_ANUNCIOS" -> {
                            // Log.d("App", msgServidor.getParams().get(1))

                            var imagenesAnuncios: ArrayList<ByteArray> = ArrayList()
                            var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(2))
                            for (i in 0 until cantAnuncios){
                                var longitudImg = this.dis?.readInt()?: 0
                                Log.d("App", longitudImg.toString())
                                var bytesImg = ByteArray(longitudImg)
                                this.dis?.readFully(bytesImg)
                                imagenesAnuncios.add(bytesImg)
                            }

                            var anuncios: List<Anuncio> = mapper.readValue(msgServidor.getParams().get(1), object: TypeReference<List<Anuncio>>(){})
                            aniadirAnuncios(anuncios, imagenesAnuncios)

                        }
                        "ANUNCIO_GUARDADO" -> {
                            handler.post{
                                showMsg.invoke(context, "Anuncio guardado correctamente")
                            }
                        }
                        "ANUNCIO_ELIMINADO_GUARDADOS" -> {
                            handler.post{
                                showMsg.invoke(context, "Anuncio eliminado de guardados")
                            }
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
        if (_uiState.value.cargando) return

        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)

        var msg = Mensaje()
        msg.setTipo("OBTENER_ANUNCIOS")
        msg.addParam(filtroJSON)
        msg.addParam(filtro?.tipoFiltro?: "Todo")
        msg.addParam(if (primeraCarga) "si" else "no")
        msg.addParam((conectionViewModel.uiState.value.usuario?.idUsuario?: 2).toString())

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()

        _uiState.value = _uiState.value.copy( cargando = true )
    }

    fun aniadirAnuncios(anunciosNuevos: List<Anuncio>, imagenes: List<ByteArray>){
        _uiState.value = _uiState.value.copy(
            anunciosEncontrados = _uiState.value.anunciosEncontrados.toList() + anunciosNuevos,
            imagenesAnuncios = _uiState.value.imagenesAnuncios.toList() + imagenes,
            cargando = false,
            noHayMasAnuncios = anunciosNuevos.size < 2
        )
    }

    fun vaciarAnuncios(){
        _uiState.value = _uiState.value.copy(anunciosEncontrados = emptyList())
    }

    fun guardarAnuncio(nombreUsuario: String, idAnuncio: Long){
        var msg = Mensaje()
        msg.setTipo("GUARDAR_ANUNCIO")
        msg.addParam(idAnuncio.toString())
        msg.addParam(nombreUsuario)

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun eliminarGuardado(nombreUsuario: String, idAnuncio: Long){
        var msg = Mensaje()
        msg.setTipo("ELIMINAR_ANUNCIO_GUARDADOS")
        msg.addParam(idAnuncio.toString())
        msg.addParam(nombreUsuario)

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }
}

class AppViewModelFactory(
    private val conectionViewModel: ConectionViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(conectionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}