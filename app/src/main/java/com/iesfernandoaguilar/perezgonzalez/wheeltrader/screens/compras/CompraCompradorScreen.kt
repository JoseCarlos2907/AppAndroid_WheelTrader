package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewModelScope
import com.github.gcacace.signaturepad.views.SignaturePad
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.element.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

@Composable
fun CompraCompradorScreen(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    onOfrecer: () -> Unit,
    modifier: Modifier = Modifier
){
    var bitmaps by remember { mutableStateOf<List<Bitmap>>(emptyList()) }
    val appUiState by appViewModel.uiState.collectAsState()

    var comision = (appUiState.anuncioSeleccionado!!.precio * 5) / 100
    var total = appUiState.anuncioSeleccionado!!.precio + comision

    val context = LocalContext.current

    var firma by remember { mutableStateOf<Bitmap?>(null) }
    val campoFirma = remember { SignaturePad(context, null) }

    LaunchedEffect(Unit) {
        bitmaps = paginasPDF(File(context.cacheDir, "Temp_Aplanado.pdf"))
    }

    LaunchedEffect(Unit) {
        campoFirma.setOnSignedListener(object: SignaturePad.OnSignedListener{
            override fun onStartSigning() {}

            override fun onSigned() {
                val bitmapFirma = campoFirma.signatureBitmap
                firma = bitmapFirma
            }

            override fun onClear() {}
        })
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black, Color(0xFF525151)),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                )
            )
    ) {
        item{
            if(bitmaps.isNotEmpty()){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.documento_de_acuerdo),
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState())
                        .padding(12.dp)
                        .height(480.dp)
                ) {
                    bitmaps.forEach{ bitmap ->
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = stringResource(R.string.des_imagen_pagina_pdf),
                                modifier = Modifier
                                    .padding(12.dp)
                                    .background(color = Color.White)
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(top = 12.dp)
            ) {
                Text(
                    text = stringResource(R.string.texto_precio_total),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3F)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_precio_vehiculo),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = appUiState.anuncioSeleccionado?.precio.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3F)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_comision),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = comision.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 4.dp,
                        modifier = Modifier.padding(vertical = 12.dp, horizontal = 14.dp)
                    )

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.3F)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_precio_total),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = total.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = stringResource(R.string.texto_firma),
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = Color.White)
                ) {
                    AndroidView(
                        factory = { campoFirma },
                        modifier = Modifier.fillMaxSize()
                    )

                    IconButton (
                        onClick = {
                            campoFirma.clear()
                            firma = null
                        },
                        colors = IconButtonDefaults.iconButtonColors(containerColor = Color.Black),
                        modifier = Modifier
                            .align(alignment = Alignment.TopEnd)
                            .size(40.dp)
                            .padding(top = 4.dp, end = 4.dp)
                            .clip(RectangleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = stringResource(R.string.desc_icono_borrar_limpiar),
                            tint = Color.White
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Button(
                    onClick = {
                        firma?.let {
                            insertarFirmaEnPDF(
                                context = context,
                                firma = it,
                                x = 50F,
                                y = 390F
                            )
                        }

                        val pdfFirmado = File(context.cacheDir, "Temp_Firmado.pdf")
                        val bytesPDF = pdfFirmado.readBytes()

                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            appViewModel.compradorOfreceCompra(
                                bytesPdf = bytesPDF,
                                idAnuncio = appUiState.anuncioSeleccionado!!.idAnuncio,
                                idVendedor = appUiState.anuncioSeleccionado!!.vendedor!!.idUsuario,
                                idComprador = conectionUiState.usuario!!.idUsuario
                            )
                        }

                        onOfrecer()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                ) {
                    Text(
                        text = stringResource(R.string.texto_enviar_oferta)
                    )
                }
            }
        }
    }
}

private fun paginasPDF(file: File): List<Bitmap>{
    val bitmaps = mutableListOf<Bitmap>()
    val descriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
    val renderer = PdfRenderer(descriptor)

    for (i in 0 until renderer.pageCount){
        renderer.openPage(i).use { page ->
            val bitmap = Bitmap.createBitmap((page.width*1.5).toInt(), (page.height*1.5).toInt(), Bitmap.Config.ARGB_8888)
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            bitmaps.add(bitmap)
        }
    }

    renderer.close()
    descriptor.close()

    return bitmaps
}

private fun insertarFirmaEnPDF(
    context: Context,
    firma: Bitmap,
    x: Float,
    y: Float,
    numPagina: Int = 2,
    escala: Float = 0.17F
){
    val archivoPDF = File(context.cacheDir, "Temp.pdf")
    val reader = PdfReader(archivoPDF)
    val pdfTemporalFirmado = File(context.cacheDir, "Temp_Firmado.pdf")
    val writer = PdfWriter(pdfTemporalFirmado)
    val documentoPDF = PdfDocument(reader, writer)
    val pagina = documentoPDF.getPage(numPagina)

    val imageData = bitmapAImageData(firma)
    val imagen = Image(imageData).scale(escala, escala).setFixedPosition(numPagina, x, y)

    val canvas = com.itextpdf.layout.Canvas(pagina, pagina.pageSize)
    canvas.add(imagen)

    canvas.close()
    documentoPDF.close()
}

private fun bitmapAImageData(bitmap: Bitmap): ImageData{
    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
    val byteArray = baos.toByteArray()
    return ImageDataFactory.create(byteArray)
}