package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Anuncio
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ListaAnuncios(
    appViewModel: AppViewModel,
    appUiState: AppUiState,
    filtrosUiState: FiltrosUiState,
    modifier: Modifier = Modifier
) {
    DisposableEffect(Unit) {
        onDispose {
            // appViewModel.vaciarAnuncios()
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
        if (appUiState.anunciosEncontrados.isEmpty()) {
            /*CircularProgressIndicator(
                modifier = Modifier.width(100.dp).height(100.dp)
            )*/
            LaunchedEffect (Unit) {
                withContext(Dispatchers.IO){
                    appViewModel.obtenerAnuncios(filtrosUiState.filtroTodo, true)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(appUiState.anunciosEncontrados) { anuncio ->
                    CardAnuncio(anuncio = anuncio)
                }

                item {
                    CircularProgressIndicator()
                    LaunchedEffect(Unit) {
                        withContext(Dispatchers.IO){
                            appViewModel.obtenerAnuncios(filtrosUiState.filtroTodo, false)
                            Log.d("App", "Otra carga: " + appUiState.anunciosEncontrados.size)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CardAnuncio(
    anuncio: Anuncio,
    modifier: Modifier = Modifier
){
    Row {
        Text(
            text = "ID: ${anuncio.idAnuncio}",
            color = Color.White
        )
    }
}