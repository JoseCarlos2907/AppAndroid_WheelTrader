package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
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

    lateinit var anuncioPublicar: Anuncio
    lateinit var imagenesPublicar: List<ByteArray>

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
                    var msgRespuesta: Mensaje
                    // Log.d("App", tipo)
                    when(tipo){
                        "ENVIA_ANUNCIOS" -> {
                            // Log.d("App", msgServidor.getParams().get(1))

                            var imagenesAnuncios: ArrayList<ByteArray> = ArrayList()
                            var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(2))
                            for (i in 0 until cantAnuncios){
                                var longitudImg = this.dis?.readInt()?: 0
                                // Log.d("App", longitudImg.toString())
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

                        "DATOS_VALIDOS" -> {
                            if("si".equals(msgServidor.getParams().get(0))){
                                msgRespuesta = Mensaje()
                                msgRespuesta.setTipo("PUBLICAR_ANUNCIO")

                                var anuncioJSON = mapper.writeValueAsString(anuncioPublicar)
                                msgRespuesta.addParam(anuncioJSON)
                                msgRespuesta.addParam(imagenesPublicar.size.toString())

                                this.dos?.writeUTF(Serializador.codificarMensaje(msgRespuesta))
                                this.dos?.flush()

                                for (i in 0 until imagenesPublicar.size){
                                    this.dos?.writeInt(imagenesPublicar.get(i).size)
                                    this.dos?.flush()

                                    this.dos?.write(imagenesPublicar.get(i))
                                    this.dos?.flush()
                                }

                            }else if("no".equals(msgServidor.getParams().get(0))){
                                handler.post{
                                    showMsg.invoke(context, "Datos introducidos no válidos")
                                }
                            }
                        }

                        "ANUNCIO_PUBLICADO" -> {
                            handler.post{
                                showMsg.invoke(context, "Anuncio publicado correctamente")
                            }

                            _uiState.value = _uiState.value.copy(goToHome = true)
                        }

                        "ENVIA_IMAGENES" -> {
                            var imagenesAnuncioSeleccionado: ArrayList<ByteArray> = ArrayList()
                            var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(0))
                            Log.d("App", "Cantidad de imagenes: " + cantAnuncios.toString())
                            for (i in 0 until cantAnuncios){
                                var longitudImg = this.dis?.readInt()?: 0
                                // Log.d("App", longitudImg.toString())
                                var bytesImg = ByteArray(longitudImg)
                                this.dis?.readFully(bytesImg)
                                imagenesAnuncioSeleccionado.add(bytesImg)
                            }

                            _uiState.value = _uiState.value.copy(imagenesAnuncioSeleccionado = imagenesAnuncioSeleccionado)
                            _uiState.value = _uiState.value.copy(goToDetalle = true)
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
        // Log.d("App", filtroJSON)

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
        _uiState.value = _uiState.value.copy(
            anunciosEncontrados = emptyList(),
            imagenesAnuncios = emptyList()
        )
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

    fun getBytesFromUri(uri: Uri?): ByteArray?{
        var imagen: ByteArray? = null
        try {
            if(uri != null){
                this.context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    imagen = inputStream.readBytes()
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

        return  imagen
    }

    fun publicarAnuncio(anuncio: Anuncio, imagenes: List<ByteArray>){
        anuncioPublicar = anuncio
        imagenesPublicar = imagenes

        var mapper = ObjectMapper()
        var vcJSON = mapper.writeValueAsString(anuncio.valoresCaracteristicas)

        var msg = Mensaje()
        msg.setTipo("COMPROBAR_DATOS_VEHICULO")
        msg.addParam(vcJSON)

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun obtenerImagenesAnuncioSel(idAnuncio: Long){
        var msg = Mensaje()
        msg.setTipo("OBTENER_IMAGENES")
        msg.addParam(idAnuncio.toString())

        this.dos?.writeUTF(Serializador.codificarMensaje(msg))
        this.dos?.flush()
    }

    fun cambiarAnuncioSeleccionado(anuncio: Anuncio){
        _uiState.value = _uiState.value.copy(anuncioSeleccionado = anuncio.copy())
    }

    fun salirDetalleAnuncio(){
        _uiState.value = _uiState.value.copy(
            anuncioSeleccionado = null,
            imagenesAnuncioSeleccionado = emptyList(),
            goToDetalle = false
        )
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