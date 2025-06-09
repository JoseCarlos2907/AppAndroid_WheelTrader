package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.compras

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Venta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun MisComprasScreen(
    appViewModel: AppViewModel,
    filtrosUiState: FiltrosUiState,
    conectionUiState: ConectionUiState,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    LaunchedEffect(listState) {
        snapshotFlow {
            val ultimoAnuncioVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val anunciosTotales = listState.layoutInfo.totalItemsCount
            ultimoAnuncioVisible to anunciosTotales
        }.collect{ (ultimoAnuncioVisible, anunciosTotales) ->
            if(ultimoAnuncioVisible != null && ultimoAnuncioVisible >= anunciosTotales-1 && !appUiState.cargando && !appUiState.noHayMasCompras){
                withContext(Dispatchers.IO){
                    appViewModel.obtenerCompras(filtrosUiState.filtro!!, false)
                    filtrosUiState.filtro!!.pagina++
                }
            }
        }
    }

    LaunchedEffect (Unit) {
        withContext(Dispatchers.IO){
            appViewModel.vaciarCompras()
            appViewModel.obtenerCompras(filtrosUiState.filtro!!, true)
            filtrosUiState.filtro!!.pagina++
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
                items = appUiState.comprasEncontradas,
                key = { venta -> venta.idVenta }
            ) { venta ->
                CardCompra(venta = venta)
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
fun CardCompra(
    venta: Venta,
    modifier: Modifier = Modifier
){
    var marca = ""
    var modelo = ""

    for (vc in venta.anuncio!!.valoresCaracteristicas!!){
        if(vc.nombreCaracteristica.contains("Marca_")){
            marca = vc.valor
        }else if(vc.nombreCaracteristica.contains("Modelo_")){
            modelo = vc.valor
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .fillMaxSize()
                .height(160.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.65F)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier
                            .fillMaxSize()
                            .weight(0.5F)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_marca_modelo, marca, modelo),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Start
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End,
                        modifier = modifier
                            .fillMaxSize()
                            .weight(0.35F)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.texto_fin_garantia,
                                venta.fechaFinGarantia
                            ),
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.End
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(0.5F)
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier
                            .fillMaxSize()
                            .weight(0.5F)
                    ) {
                        Text(
                            text = stringResource(R.string.precio, venta.anuncio!!.precio),
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Start
                        )
                    }

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End,
                        modifier = modifier
                            .fillMaxSize()
                            .weight(0.5F)
                    ) {
                        Text(
                            text = stringResource(
                                R.string.texto_comprado_a,
                                venta.anuncio!!.vendedor!!.nombreUsuario
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelMedium,
                            textAlign = TextAlign.End
                        )
                    }
                }
            }
        }
    }
}