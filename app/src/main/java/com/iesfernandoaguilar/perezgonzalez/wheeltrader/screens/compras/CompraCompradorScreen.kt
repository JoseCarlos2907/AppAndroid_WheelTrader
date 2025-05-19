package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.io.File
import java.io.FileOutputStream

@Composable
fun CompraCompradorScreen(
    // pdfFile: File,
    modifier: Modifier = Modifier
){
    var bitmaps by remember { mutableStateOf<List<Bitmap>>(emptyList()) }

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // bitmaps = paginasPDF(pdfFile)
        bitmaps = paginasPDF(File(context.filesDir, "prueba_Python_27_02_25.pdf"))
        // copyPdfFromAssets(context, "prueba_Python_27_02_25.pdf")
    }

    LazyColumn(
        modifier = modifier.fillMaxSize().background(
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
                    modifier = Modifier.fillMaxWidth().height(60.dp)
                ) {
                    Text(
                        text = "Documento de acuerdo",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()).padding(12.dp).height(400.dp)
                ) {
                    bitmaps.forEach{ bitmap ->
                        Column(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.padding(12.dp).background(color = Color.White).fillMaxSize()
                            )
                        }
                    }
                }
            }else{
                Text(
                    text = "Casi"
                )
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 12.dp)
            ) {
                Text(
                    text = "Precio total",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
            // TODO: Precios


            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().height(60.dp)
            ) {
                Text(
                    text = "Firma",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxSize()
                )
            }
            // TODO: Canvas Firma


            // TODO: Botón enviar oferta
            Button(
                onClick = {}
            ) {
                Text(
                    text = "Enviar oferta"
                )
            }
        }
    }
}

private fun copyPdfFromAssets(context: Context, nombreArchivo: String): File{
    val file = File(context.filesDir, nombreArchivo)

    if(!file.exists()){
        context.assets.open(nombreArchivo).use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
    }

    return file
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