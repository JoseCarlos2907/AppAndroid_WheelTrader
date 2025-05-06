package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.tipoVehiculoCard

@Composable
fun ListaTiposVehiculos(
    appNavController: NavController,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
){

    val opciones = listOf("Coches", "Motos", "Camionetas", "Camiones", "Maquinaria")
    val imagenes = listOf(R.drawable.fotofiltrocoche, R.drawable.fotofiltromoto, R.drawable.fotofiltrocamioneta, R.drawable.fotofiltrocamion, R.drawable.fotofiltromaquinaria)
    val onClicks = listOf(
        {
            appNavController.navigate(AppScreens.PublicarCoche.screenName)
        },
        {},
        {},
        {},
        {},
    )

    Column(
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth().weight(0.25F)
        ){
            Text(
                text = "Especificaciones\ndel Coche",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier.fillMaxSize().weight(0.75F)
        ) {
            items(opciones) { opcion ->
                tipoVehiculoCard(
                    onClick = onClicks.get(opciones.indexOf(opcion)),
                    imagenes.get(opciones.indexOf(opcion)),
                    opcion
                )
            }
        }
    }

}
