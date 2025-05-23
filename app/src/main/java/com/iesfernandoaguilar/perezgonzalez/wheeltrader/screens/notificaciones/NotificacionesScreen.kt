package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.notificaciones

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Notificacion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroNotificaciones
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun NotificacionesScreen(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    goToCompraVendedor: () -> Unit,
    goToPayPalScreen: () -> Unit,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    var filtroNotificaciones = FiltroNotificaciones(conectionUiState.usuario!!.idUsuario)

    LaunchedEffect(listState) {
        snapshotFlow {
            val ultimaNotificacionVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
            val notificacionesTotales = listState.layoutInfo.totalItemsCount
            ultimaNotificacionVisible to notificacionesTotales
        }.collect{ (ultimaNotificacionVisible, notificacionesTotales) ->
            if(ultimaNotificacionVisible != null && ultimaNotificacionVisible >= notificacionesTotales-1 && !appUiState.cargando && !appUiState.noHayMasNotificaciones){
                withContext(Dispatchers.IO){
                    appViewModel.obtenerNotificaciones(filtroNotificaciones, false)
                    filtroNotificaciones.pagina++
                }
            }
        }
    }

    LaunchedEffect(appUiState.goToCompraVendedor) {
        if(appUiState.goToCompraVendedor){
            goToCompraVendedor()
            appViewModel.reiniciarGoToCompraVendedor()
        }
    }

    LaunchedEffect(appUiState.goToPayPalScreen) {
        if(appUiState.goToPayPalScreen){
            goToPayPalScreen()
            appViewModel.asignarGoToPayPalScreen(false)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            appViewModel.vaciarNotificaciones()
        }
    }

    Column(
        modifier = modifier.fillMaxSize().background(
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
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().height(60.dp).padding(top = 20.dp)
                ) {
                    Text(
                        text = "Mis Notificaciones",
                        color = Color.White,
                        style = MaterialTheme.typography.titleLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }

            items(
                items = appUiState.notificacionesEncontrados,
                key = { notificacion -> notificacion.idNotificacion }
            ) { notificacion ->
                CardNotificacion(
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState,
                    notificacion = notificacion,
                    goToPayPalScreen = goToPayPalScreen
                )
            }

            if(appUiState.cargando){
                item{
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun CardNotificacion(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    goToPayPalScreen: () -> Unit,
    notificacion: Notificacion,
    modifier: Modifier = Modifier
){
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        modifier = modifier.height(160.dp).padding(6.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ){
            Column(
                modifier = Modifier.weight(0.15F)
            ) {
                Image(
                    painter = when(notificacion.tipo){
                        "OFERTA_ANUNCIO" -> painterResource(R.drawable.iconoofertarecibida)
                        "OFERTA_ACEPTADA" -> painterResource(R.drawable.iconoofertaaceptada)
                        "OFERTA_RECHAZADA" -> painterResource(R.drawable.iconoofertarechazada)
                        else -> painterResource(R.drawable.iconoinformacion)
                    },
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier.weight(0.85F)
            ) {
                Row(
                    modifier = Modifier.weight(0.2F).fillMaxWidth()
                ) {
                    Text(
                        text = notificacion.titulo!!,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    modifier = Modifier.weight(0.5F).fillMaxWidth().padding(start = 6.dp)
                ) {
                    Text(
                        text = notificacion.mensaje!!,
                        textAlign = TextAlign.Start,
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.weight(0.3F).fillMaxWidth()
                ) {
                    if(!"OFERTA_RECHAZADA".equals(notificacion.tipo)){
                        Button(
                            onClick = {
                                appViewModel.seleccionarNotificacion(notificacion.idNotificacion, notificacion.anuncio!!.idAnuncio, notificacion.usuarioEnvia!!.idUsuario, notificacion.anuncio!!.precio)
                                appViewModel.viewModelScope.launch(Dispatchers.IO) {
                                    var precioTotal = notificacion.anuncio!!.precio + (notificacion.anuncio!!.precio * 0.05)
                                    if("OFERTA_ACEPTADA".equals(notificacion.tipo)){
                                        appViewModel.usuarioPaga(
                                            idComprador = conectionUiState.usuario!!.idUsuario,
                                            idVendedor = notificacion.usuarioEnvia!!.idUsuario,
                                            precio = precioTotal
                                        )
                                    }else if("OFERTA_ANUNCIO".equals(notificacion.tipo)){
                                        appViewModel.obtenerPDFAcuerdoVendedor(notificacion.usuarioEnvia!!.idUsuario, notificacion.anuncio!!.idAnuncio)
                                    }

                                }

                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                            enabled = if ("RESPONDIDO".equals(notificacion.estado)) false else true
                        ) {
                            Text(
                                text = if("OFERTA_ACEPTADA".equals(notificacion.tipo)) "Pagar" else "Ver Oferta",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    WheelTraderTheme {
        // CardNotificacion(Notificacion())
    }
}