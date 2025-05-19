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
            if(ultimaNotificacionVisible != null && ultimaNotificacionVisible >= notificacionesTotales-1 && !appUiState.cargando && !appUiState.noHayMasAnuncios){
                withContext(Dispatchers.IO){
                    appViewModel.obtenerNotificaciones(filtroNotificaciones, false)
                    filtroNotificaciones.pagina++
                }
            }
        }
    }

    LaunchedEffect (Unit) {
        withContext(Dispatchers.IO){
            appViewModel.obtenerNotificaciones(filtroNotificaciones, true)
            filtroNotificaciones.pagina++
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
                    notificacion = notificacion
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
                    Button(
                        onClick = {
                            // TODO: Según el tipo de botón que sea mandar a pagar, responder oferta o no mostrar
                            // TODO: Con el texto igual
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        enabled = if ("NO_LEIDO".equals(notificacion.estado)) true else false
                    ) {
                        Text(
                            text = "Ver Oferta",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
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