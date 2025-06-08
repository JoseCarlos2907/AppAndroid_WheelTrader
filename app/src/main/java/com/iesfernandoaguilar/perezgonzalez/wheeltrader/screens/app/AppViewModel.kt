package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.interfaces.IFiltro
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Mensaje
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Notificacion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Reporte
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Venta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.Serializador
import com.itextpdf.forms.PdfAcroForm
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.EOFException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader
import java.nio.file.Files
import java.nio.file.Path
import java.util.Base64
import java.util.Properties

class AppViewModel(
    private val conectionViewModel: ConectionViewModel
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private var dis: DataInputStream? = null
    private var dos: DataOutputStream? = null

    lateinit var showMsg: ((Context, String) -> Unit)

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    lateinit var handler: Handler
    var lectorJob: Job? = null

    lateinit var anuncioPublicar: Anuncio
    lateinit var imagenesPublicar: List<ByteArray>

    fun confVM(context: Context) {
        this.context = context
        this.handler = Handler(Looper.getMainLooper())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun escucharDelServidor_App() {
        if (lectorJob != null) return

        lectorJob = viewModelScope.launch(Dispatchers.IO) {
            dis = conectionViewModel.getDataInputStream()
            dos = conectionViewModel.getDataOutputStream()

            var mapper = jacksonObjectMapper()
            var cierraSesion = false

            while (this.isActive && !cierraSesion) {
                try {
                    var linea: String = dis?.readUTF() ?: "NoInfo"
                    Log.d("App", linea)
                    var msgServidor: Mensaje = Serializador.decodificarMensaje(linea)
                    // Log.d("App", "Aqui no llega")

                    var tipo = msgServidor.getTipo()
                    var msgRespuesta: Mensaje
                    // Log.d("App", tipo)
                    when (tipo) {
                        "ENVIA_ANUNCIOS" -> {
                            // Log.d("App", msgServidor.getParams().get(1))

                            var imagenesAnuncios: ArrayList<ByteArray> = ArrayList()
                            var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(2))
                            for (i in 0 until cantAnuncios) {
                                var longitudImg = dis?.readInt() ?: 0
                                // Log.d("App", longitudImg.toString())
                                var bytesImg = ByteArray(longitudImg)
                                dis?.readFully(bytesImg)
                                imagenesAnuncios.add(bytesImg)
                            }

                            var anuncios: List<Anuncio> = mapper.readValue(
                                msgServidor.getParams().get(1),
                                object : TypeReference<List<Anuncio>>() {})
                            aniadirAnuncios(anuncios, imagenesAnuncios)
                        }

                        "ANUNCIO_GUARDADO" -> {
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "Anuncio guardado correctamente")
                            }
                        }

                        "ANUNCIO_ELIMINADO_GUARDADOS" -> {
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "Anuncio eliminado de guardados")
                            }
                        }

                        "DATOS_VALIDOS" -> {
                            if ("si".equals(msgServidor.getParams().get(0))) {
                                msgRespuesta = Mensaje()
                                msgRespuesta.setTipo("PUBLICAR_ANUNCIO")

                                var anuncioJSON = mapper.writeValueAsString(anuncioPublicar)
                                msgRespuesta.addParam(anuncioJSON)
                                msgRespuesta.addParam(imagenesPublicar.size.toString())

                                dos?.writeUTF(Serializador.codificarMensaje(msgRespuesta))
                                dos?.flush()

                                for (i in 0 until imagenesPublicar.size) {
                                    dos?.writeInt(imagenesPublicar.get(i).size)
                                    dos?.flush()

                                    dos?.write(imagenesPublicar.get(i))
                                    dos?.flush()
                                }

                            } else if ("no".equals(msgServidor.getParams().get(0))) {
                                withContext(Dispatchers.Main) {
                                    showMsg.invoke(context, "Datos introducidos no válidos")
                                }
                            }
                        }

                        "ANUNCIO_PUBLICADO" -> {
                            withContext(Dispatchers.Main) {
                                showMsg.invoke(context, "Anuncio publicado correctamente")
                            }

                            _uiState.value = _uiState.value.copy(goToHome = true)
                        }

                        "ENVIA_IMAGENES" -> {
                            var imagenesAnuncioSeleccionado: ArrayList<ByteArray> = ArrayList()
                            var cantAnuncios = Integer.parseInt(msgServidor.getParams().get(0))
                            Log.d("App", "Cantidad de imagenes: " + cantAnuncios.toString())
                            for (i in 0 until cantAnuncios) {
                                var longitudImg = dis?.readInt() ?: 0
                                // Log.d("App", longitudImg.toString())
                                var bytesImg = ByteArray(longitudImg)
                                dis?.readFully(bytesImg)
                                imagenesAnuncioSeleccionado.add(bytesImg)
                            }

                            _uiState.value =
                                _uiState.value.copy(imagenesAnuncioSeleccionado = imagenesAnuncioSeleccionado)
                            _uiState.value = _uiState.value.copy(goToDetalle = true)
                        }

                        "ENVIA_NOTIFICACIONES" -> {
                            var notificaciones = mapper.readValue(
                                msgServidor.getParams().get(0),
                                object : TypeReference<List<Notificacion>>() {});
                            aniadirNotificaciones(notificaciones)
                        }

                        "ENVIA_PDF_ACUERDO" -> {
                            var longitudPDF = msgServidor.getParams().get(0).toInt()
                            var bytesPDF = ByteArray(longitudPDF)
                            dis?.readFully(bytesPDF)

                            // Creo dos PDF, uno con los campos aplanados para mostrar y otro que es el que se va a modificar y enviar
                            val archivoPDF = File(context.cacheDir, "Temp.pdf")
                            aplanarPDF(bytesPDF)
                            if (archivoPDF.exists()) archivoPDF.delete()
                            archivoPDF.createNewFile()
                            var os = FileOutputStream(archivoPDF)
                            os.write(bytesPDF)
                            os.close()

                            _uiState.value = _uiState.value.copy(goToCompraComprador = true)
                        }

                        "ENVIA_PDF_ACUERDO_VENDEDOR" -> {
                            var longitudPDF = msgServidor.getParams().get(0).toInt()
                            var bytesPDF = ByteArray(longitudPDF)
                            dis?.readFully(bytesPDF)

                            val archivoPDF = File(context.cacheDir, "Temp.pdf")
                            aplanarPDF(bytesPDF)
                            if (archivoPDF.exists()) archivoPDF.delete()
                            archivoPDF.createNewFile()

                            var os = FileOutputStream(archivoPDF)
                            os.write(bytesPDF)
                            os.close()

                            _uiState.value = _uiState.value.copy(goToCompraVendedor = true)
                        }

                        "ENVIA_URL_PAGO" -> {
                            asignarUrlPayPal(msgServidor.getParams().get(0))
                            asignarGoToPayPalScreen(true)
                        }

                        "ENVIA_ESTADO_PAGO" -> {
                            Log.d("App", "Comprueba estado pago: " + msgServidor.getParams().get(0))
                            if ("si".equals(msgServidor.getParams().get(0))) {
                                _uiState.value = _uiState.value.copy(confirmaPago = true)
                                handler.post {
                                    showMsg.invoke(context, "Pago efectuado correctamente")
                                }
                            } else if ("error".equals(msgServidor.getParams().get(0))) {
                                handler.post {
                                    showMsg.invoke(context, "Error al hacer el pago")
                                }
                            }
                        }

                        "REPORTE_REALIZADO" -> {
                            if ("si".equals(msgServidor.getParams().get(0))) {
                                handler.post {
                                    showMsg.invoke(context, "Usuario reportado correctamente")
                                }
                            } else if ("no".equals(msgServidor.getParams().get(0))) {
                                handler.post {
                                    showMsg.invoke(context, "Ya has reportado a este usuario")
                                }
                            }
                        }

                        "ENVIA_SALT_REINICIO" -> {
                            _uiState.value = _uiState.value.copy(
                                saltUsuarioReinicio = Base64.getDecoder()
                                    .decode(msgServidor.getParams().get(0))
                            )
                        }

                        "CONTRASENIA_REINICIADA" -> {
                            handler.post {
                                showMsg.invoke(context, "Contraseña reiniciada correctamente")
                            }
                        }

                        "ENVIA_VENTAS" -> {
                            var ventas = mapper.readValue(
                                msgServidor.getParams().get(0),
                                object : TypeReference<List<Venta>>() {});
                            aniadirCompras(ventas)
                        }

                        "SESION_CERRADA" -> {
                            Log.d("App", "Sesión cerrada")
                            cierraSesion = true
                            break;
                        }

                        "" -> {

                        }

                        else -> {
                            Log.d("App", "Raro que entre en el else del when")
                        }
                    }
                } catch (e: CancellationException) {
                    Log.d("App", "Cancelando corrutina de app")
                } catch (e: EOFException) {
                    Log.d("App", "Se ha cerrado el flujo del socket")
                    reconectar()
                } catch (e: IOException) {
                    Log.d("App", "Error de conexión: " + e.message)
                    reconectar()
                }
            }

        }
        lectorJob?.start()
    }

    fun pararEscuchaServidor_App() {
        lectorJob?.let {
            if (it.isActive) {
                Log.d("App", "Hilo parado")
                it.cancel()
            }
        }
        lectorJob = null
    }

    fun obtenerAnuncios(filtro: IFiltro?, primeraCarga: Boolean) {
        if (_uiState.value.cargando) return

        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)
        // Log.d("App", filtroJSON)

        var msg = Mensaje()
        msg.setTipo("OBTENER_ANUNCIOS")
        msg.addParam(filtroJSON)
        msg.addParam(filtro?.tipoFiltro ?: "Todo")
        msg.addParam(if (primeraCarga) "si" else "no")
        msg.addParam((conectionViewModel.uiState.value.usuario?.idUsuario ?: 2).toString())

        enviarMensaje(msg)

        _uiState.value = _uiState.value.copy(cargando = true)
    }

    fun aniadirAnuncios(anunciosNuevos: List<Anuncio>, imagenes: List<ByteArray>) {
        _uiState.value = _uiState.value.copy(
            anunciosEncontrados = _uiState.value.anunciosEncontrados.toList() + anunciosNuevos,
            imagenesAnuncios = _uiState.value.imagenesAnuncios.toList() + imagenes,
            cargando = false,
            noHayMasAnuncios = anunciosNuevos.size < 2
        )
    }

    fun vaciarAnuncios() {
        _uiState.value = _uiState.value.copy(
            anunciosEncontrados = emptyList(),
            imagenesAnuncios = emptyList()
        )
    }

    fun guardarAnuncio(nombreUsuario: String, idAnuncio: Long) {
        var msg = Mensaje()
        msg.setTipo("GUARDAR_ANUNCIO")
        msg.addParam(idAnuncio.toString())
        msg.addParam(nombreUsuario)

        enviarMensaje(msg)
    }

    fun eliminarGuardado(nombreUsuario: String, idAnuncio: Long) {
        var msg = Mensaje()
        msg.setTipo("ELIMINAR_ANUNCIO_GUARDADOS")
        msg.addParam(idAnuncio.toString())
        msg.addParam(nombreUsuario)

        enviarMensaje(msg)
    }

    fun getBytesFromUri(uri: Uri?): ByteArray? {
        var imagen: ByteArray? = null
        try {
            if (uri != null) {
                this.context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    imagen = inputStream.readBytes()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return imagen
    }

    fun publicarAnuncio(anuncio: Anuncio, imagenes: List<ByteArray>) {
        anuncioPublicar = anuncio
        imagenesPublicar = imagenes

        var mapper = ObjectMapper()
        var vcJSON = mapper.writeValueAsString(anuncio.valoresCaracteristicas)

        var msg = Mensaje()
        msg.setTipo("COMPROBAR_DATOS_VEHICULO")
        msg.addParam(vcJSON)

        enviarMensaje(msg)
    }

    fun obtenerImagenesAnuncioSel(idAnuncio: Long) {
        var msg = Mensaje()
        msg.setTipo("OBTENER_IMAGENES")
        msg.addParam(idAnuncio.toString())

        enviarMensaje(msg)
    }

    fun cambiarAnuncioSeleccionado(anuncio: Anuncio) {
        _uiState.value = _uiState.value.copy(anuncioSeleccionado = anuncio.copy())
    }

    fun salirDetalleAnuncio() {
        _uiState.value = _uiState.value.copy(
            imagenesAnuncioSeleccionado = emptyList(),
            goToDetalle = false,
            goToCompraComprador = false
        )
    }

    fun obtenerNotificaciones(filtro: IFiltro, primeraCarga: Boolean) {
        if (_uiState.value.cargando) return

        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)
        // Log.d("App", filtroJSON)

        var msg = Mensaje()
        msg.setTipo("OBTENER_NOTIFICACIONES")
        msg.addParam(filtroJSON)
        msg.addParam(if (primeraCarga) "si" else "no")

        enviarMensaje(msg)

        _uiState.value = _uiState.value.copy(cargando = true)
    }

    fun aniadirNotificaciones(notificaciones: List<Notificacion>) {
        _uiState.value = _uiState.value.copy(
            notificacionesEncontrados = _uiState.value.notificacionesEncontrados.toList() + notificaciones,
            cargando = false,
            noHayMasNotificaciones = notificaciones.size < 4
        )
    }

    fun vaciarNotificaciones() {
        _uiState.value = _uiState.value.copy(
            notificacionesEncontrados = emptyList(),
            cargando = false,
            noHayMasNotificaciones = false
        )
    }

    fun obtenerPDFAcuerdo(idComprador: Long, idAnuncio: Long, tipoAnuncio: String) {
        var msg = Mensaje()
        msg.setTipo("OBTENER_PDF_ACUERDO")
        msg.addParam(idComprador.toString())
        msg.addParam(idAnuncio.toString())
        msg.addParam(tipoAnuncio)

        enviarMensaje(msg)
    }

    fun aplanarPDF(bytesPdf: ByteArray) {
        val archivoPdfAplanado = File(context.cacheDir, "Temp_Aplanado.pdf")
        if (archivoPdfAplanado.exists()) archivoPdfAplanado.delete()
        archivoPdfAplanado.createNewFile()

        val reader = PdfReader(ByteArrayInputStream(bytesPdf))
        val writer = PdfWriter(archivoPdfAplanado)
        val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(reader, writer)

        val formulario = PdfAcroForm.getAcroForm(pdfDocument, true)
        // Aplanar los campos para que se puedan ver en pantalla
        formulario.flattenFields()

        pdfDocument.close()
    }

    fun compradorOfreceCompra(
        bytesPdf: ByteArray,
        idComprador: Long,
        idAnuncio: Long,
        idVendedor: Long
    ) {
        var msg = Mensaje()
        msg.setTipo("COMPRADOR_OFRECE_COMPRA")
        msg.addParam(bytesPdf.size.toString())
        msg.addParam(idComprador.toString())
        msg.addParam(idAnuncio.toString())
        msg.addParam(idVendedor.toString())

        enviarMensaje(msg)

        this.dos?.write(bytesPdf)
        this.dos?.flush()
    }

    fun reiniciarGoToCompraComprador() {
        _uiState.value = _uiState.value.copy(goToCompraComprador = false)
    }

    fun obtenerPDFAcuerdoVendedor(idComprador: Long, idAnuncio: Long) {
        var msg = Mensaje()
        msg.setTipo("OBTENER_PDF_ACUERDO_VENDEDOR")
        msg.addParam(idComprador.toString())
        msg.addParam(idAnuncio.toString())

        enviarMensaje(msg)
    }

    fun reiniciarGoToCompraVendedor() {
        _uiState.value = _uiState.value.copy(goToCompraVendedor = false)
    }

    fun vendedorConfirmaCompra(
        bytesPdf: ByteArray,
        idComprador: Long,
        idAnuncio: Long,
        idVendedor: Long,
        idNotificacion: Long
    ) {
        var msg = Mensaje()
        msg.setTipo("VENDEDOR_CONFIRMA_COMPRA")
        msg.addParam(bytesPdf.size.toString())
        msg.addParam(idComprador.toString())
        msg.addParam(idAnuncio.toString())
        msg.addParam(idVendedor.toString())
        msg.addParam(idNotificacion.toString())

        enviarMensaje(msg)

        this.dos?.write(bytesPdf)
        this.dos?.flush()
    }

    fun vendedorRechazaCompra(
        idComprador: Long,
        idAnuncio: Long,
        idVendedor: Long,
        idNotificacion: Long
    ) {
        var msg = Mensaje()
        msg.setTipo("VENDEDOR_RECHAZA_COMPRA")
        msg.addParam(idComprador.toString())
        msg.addParam(idAnuncio.toString())
        msg.addParam(idVendedor.toString())
        msg.addParam(idNotificacion.toString())

        enviarMensaje(msg)
    }

    fun seleccionarNotificacion(
        idNotificacion: Long,
        idAnuncio: Long,
        idVendedor: Long,
        precio: Double
    ) {
        _uiState.value = _uiState.value.copy(
            idNotificacionSeleccionada = idNotificacion,
            idAnuncioNotificacionSeleccionada = idAnuncio,
            idCompradorNotificacionSeleccionada = idVendedor,
            precioAnuncioNotificacionSeleccionada = precio
        )
    }

    fun reiniciarNotificacion() {
        _uiState.value = _uiState.value.copy(
            idNotificacionSeleccionada = null,
            idAnuncioNotificacionSeleccionada = null,
            idCompradorNotificacionSeleccionada = null,
            precioAnuncioNotificacionSeleccionada = null,
        )
    }

    fun usuarioPaga(idComprador: Long, idVendedor: Long, precio: Double, idAnuncio: Long) {
        var msg = Mensaje()
        msg.setTipo("USUARIO_PAGA")
        msg.addParam(idComprador.toString())
        msg.addParam(idVendedor.toString())
        msg.addParam(precio.toString())
        msg.addParam(idAnuncio.toString())

        enviarMensaje(msg)
    }

    fun reiniciaConfirmaPago() {
        _uiState.value = _uiState.value.copy(confirmaPago = false)
    }

    fun asignarUrlPayPal(url: String) {
        _uiState.value = _uiState.value.copy(urlPayPal = url)
    }

    fun asignarGoToPayPalScreen(goToPayPalScreen: Boolean) {
        _uiState.value = _uiState.value.copy(goToPayPalScreen = goToPayPalScreen)
    }

    fun preguntarEstadoPago() {
        viewModelScope.launch(Dispatchers.IO) {
            while (!_uiState.value.confirmaPago) {
                if(_uiState.value.idNotificacionSeleccionada == null){
                    break;
                }
                var msg = Mensaje()
                msg.setTipo("OBTENER_ESTADO_PAGO")
                msg.addParam(_uiState.value.idNotificacionSeleccionada.toString())
                msg.addParam(_uiState.value.precioAnuncioNotificacionSeleccionada.toString())
                msg.addParam(_uiState.value.idAnuncioNotificacionSeleccionada.toString())

                enviarMensaje(msg)

                Thread.sleep(4000)
            }
        }
    }

    fun asignarReporte(reporte: Reporte) {
        _uiState.value = _uiState.value.copy(reporteUsuario = reporte)
    }

    fun reiniciarReporte() {
        _uiState.value = _uiState.value.copy(reporteUsuario = null)
    }

    fun reportarUsuario(reporte: Reporte) {
        var mapper = jacksonObjectMapper()
        var reporteJSON = mapper.writeValueAsString(reporte)

        var msg = Mensaje()
        msg.setTipo("REPORTAR_USUARIO")
        msg.addParam(reporteJSON)

        enviarMensaje(msg)
    }

    fun onContraseniaConfUsuarioChange(contrasenia: String) {
        _uiState.value = _uiState.value.copy(contraseniaReiniciarContrasenia = contrasenia)
    }

    fun onRepetirContraseniaConfUsuarioChange(contrasenia: String) {
        _uiState.value = _uiState.value.copy(repetirContraseniaReiniciarContrasenia = contrasenia)
    }

    fun obtenerSaltReinicio(nombreUsuario: String) {
        var msg = Mensaje()
        msg.setTipo("OBTENER_SALT_REINICIO")
        msg.addParam(nombreUsuario)

        enviarMensaje(msg)
    }

    fun vaciarSaltReinicio() {
        _uiState.value = _uiState.value.copy(saltUsuarioReinicio = null)
    }

    fun reiniciarContrasenia(nombreUsuario: String, contrasenia: String) {
        var msg = Mensaje()
        msg.setTipo("REINICIAR_CONTRASENIA")
        msg.addParam(nombreUsuario)
        msg.addParam(SecureUtils.generate512(contrasenia, _uiState.value.saltUsuarioReinicio!!))

        enviarMensaje(msg)
    }

    fun obtenerCompras(filtro: IFiltro, primeraCarga: Boolean) {
        if (_uiState.value.cargando) return

        var mapper = ObjectMapper()
        var filtroJSON = mapper.writeValueAsString(filtro)

        var msg = Mensaje()
        msg.setTipo("OBTENER_VENTAS")
        msg.addParam(filtroJSON)
        msg.addParam(if (primeraCarga) "si" else "no")

        enviarMensaje(msg)

        _uiState.value = _uiState.value.copy(cargando = true)
    }

    fun aniadirCompras(compras: List<Venta>) {
        _uiState.value = _uiState.value.copy(
            comprasEncontradas = _uiState.value.comprasEncontradas.toList() + compras,
            cargando = false,
            noHayMasCompras = compras.size < 4
        )
    }

    fun vaciarCompras() {
        _uiState.value = _uiState.value.copy(
            comprasEncontradas = emptyList(),
            cargando = false,
            noHayMasCompras = false
        )
    }

    fun cerrarSesion(idUsuario: Long) {
        var msg = Mensaje()
        msg.setTipo("CERRAR_SESION")
        msg.addParam(idUsuario.toString())

        enviarMensaje(msg)
    }

    fun reconectar() {
        val properties = Properties()
        val assetManager = context.assets

        properties.load(InputStreamReader(assetManager.open("conf.properties")))

        conectionViewModel.cerrarConexion()

        conectionViewModel.conectar(
            properties.getProperty("ADDRESS"),
            Integer.parseInt(properties.getProperty("PORT"))
        )

        this.dis = conectionViewModel.getDataInputStream()
        this.dos = conectionViewModel.getDataOutputStream()

        Log.d("App", "Se te ha vuelto a conectar a la aplicación.")
    }

    fun enviarMensaje(msg: Mensaje) {
        if (conectionViewModel.uiState.value.socket != null && !conectionViewModel.uiState.value.socket!!.isClosed) {
            this.dos?.writeUTF(Serializador.codificarMensaje(msg))
            this.dos?.flush()
        } else {
            Log.d("Login", "No se puede realizar esa acción por un error en la conexión")
        }
    }

    fun mostrarToast(msg: String) {
        handler.post {
            showMsg.invoke(context, msg)
        }
    }
}

class AppViewModelFactory(
    private val conectionViewModel: ConectionViewModel
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AppViewModel::class.java)) {
            return AppViewModel(conectionViewModel) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}