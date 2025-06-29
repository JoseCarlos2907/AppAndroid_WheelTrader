package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ListaAnuncios(
    appViewModel: AppViewModel,
    filtrosUiState: FiltrosUiState,
    conectionUiState: ConectionUiState,
    onClickAnuncio: (Anuncio) -> Unit,
    onImagenesCargadas: () -> Unit,
    modifier: Modifier = Modifier
) {
    val appUiState by appViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            // Obtengo el índicce del último anuncio visible
            val ultimoAnuncioVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            // Obtengo la cantidad total de anuncios
            val anunciosTotales = listState.layoutInfo.totalItemsCount
            ultimoAnuncioVisible to anunciosTotales
        }.collect{(ultimoAnuncioVisible, anunciosTotales) ->
            // Se comprueba que el último anuncio visible no sea nulo,
            // Cuando el último anuncio visible esté en la última posición (ya que puede ser el 3 el último visible siendo 10 elementos en total)
            // Que no esté cargando
            // Y que haya anuncios en la base de datos para seguir pidiendo, si no hay más se pone el valor a true para evitar que entre porque no hay más para cargar
            if(ultimoAnuncioVisible != null && ultimoAnuncioVisible >= anunciosTotales-1 && !appUiState.cargando && !appUiState.noHayMasAnuncios){
                withContext(Dispatchers.IO){
                    appViewModel.obtenerAnuncios(filtrosUiState.filtro, false)
                    filtrosUiState.filtro!!.pagina++
                }
            }
        }
    }

    LaunchedEffect (Unit) {
        withContext(Dispatchers.IO){
            appViewModel.vaciarAnuncios()
            appViewModel.obtenerAnuncios(filtrosUiState.filtro, true)
            filtrosUiState.filtro!!.pagina++
        }
    }

    LaunchedEffect(appUiState.goToDetalle) {
        if (appUiState.goToDetalle){
            onImagenesCargadas()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            appViewModel.vaciarAnuncios()
        }
    }

    Column(
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

        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = appUiState.anunciosEncontrados,
                key = { anuncio -> anuncio.idAnuncio }
            ) { anuncio ->
                CardAnuncio(
                    anuncio = anuncio,
                    onClickGuardar = {
                        appViewModel.viewModelScope.launch(Dispatchers.IO) {
                            if(anuncio.guardado){
                                appViewModel.eliminarGuardado(conectionUiState.usuario?.nombreUsuario?: "prueba123", anuncio.idAnuncio)
                                anuncio.guardado = false
                            }else{
                                appViewModel.guardarAnuncio(conectionUiState.usuario?.nombreUsuario?: "prueba123", anuncio.idAnuncio)
                                anuncio.guardado = true
                            }
                        }
                    },
                    onClickAnuncio = { onClickAnuncio(anuncio) },
                    bytesImagen = appUiState.imagenesAnuncios.get(appUiState.anunciosEncontrados.indexOf(anuncio))
                )
            }

            if(appUiState.cargando){
                item{
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CardAnuncio(
    anuncio: Anuncio,
    bytesImagen: ByteArray,
    onClickGuardar: () -> Unit,
    onClickAnuncio: () -> Unit,
    modifier: Modifier = Modifier
){
    var isGuardado by remember { mutableStateOf(anuncio.guardado) }

    var marca = ""
    var modelo = ""
    var anio = ""
    var km = ""

    for (vc in anuncio.valoresCaracteristicas!!){
        if(vc.nombreCaracteristica.contains("Marca_")){
            marca = vc.valor
        }else if(vc.nombreCaracteristica.contains("Modelo_")){
            modelo = vc.valor
        }else if(vc.nombreCaracteristica.contains("Anio_")){
            anio = vc.valor
        }else if(vc.nombreCaracteristica.contains("KM_")){
            km = vc.valor
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            onClick = { onClickAnuncio() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxSize()
                .height(320.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    imagenByteArray(bytesImagen)
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.texto_marca_modelo, marca, modelo),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.precio, anuncio.precio),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )
                }

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, end = 12.dp)
                ){
                    Text(
                        text = if (!"Maquinaria".equals(anuncio.tipoVehiculo)) stringResource(
                            R.string.caracteristicas_anuncio,
                            anuncio.tipoVehiculo!!,
                            anio,
                            km
                        ) else stringResource(
                            R.string.caracteristicas_anuncio_maquinaria,
                            anuncio.tipoVehiculo!!,
                            anio
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White
                    )

                    Icon(
                        painter = if (isGuardado) painterResource(R.drawable.iconoguardado) else painterResource(R.drawable.iconoguardar),
                        contentDescription = stringResource(R.string.desc_icono_guardado_o_no_guardado),
                        modifier = Modifier
                            .height(60.dp)
                            .clickable(onClick = {
                                isGuardado = !isGuardado
                                onClickGuardar()
                            }),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun imagenByteArray(
    imagen: ByteArray,
    modifier: Modifier = Modifier
){
    val bitmap = remember(imagen) {
        BitmapFactory.decodeByteArray(imagen, 0, imagen.size)
    }

    bitmap?.let {
        val imagenBitmap = it.asImageBitmap()
        Image(
            bitmap = imagenBitmap,
            contentDescription = stringResource(R.string.desc_imagen_de_anuncio_ba),
            modifier = Modifier.height(150.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCardAnuncio() {
    WheelTraderTheme {
        /*CardAnuncio(
            Anuncio(idAnuncio = 2, provincia = "pr", ciudad = "ciu", estado = "est", precio = 0.01, guardado = true, descripcion = "desc", tipoVehiculo = "tipo", fechaPublicacion = null, fechaExpiracion = null, numSerieBastidor = "serie", matricula = "mat", vendedor = null, venta = null)
        )*/
    }
}