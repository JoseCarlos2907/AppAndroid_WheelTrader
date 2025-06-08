package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.imagenByteArray
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
        {
            appNavController.navigate(AppScreens.PublicarMoto.screenName)
        },
        {
            appNavController.navigate(AppScreens.PublicarCamioneta.screenName)
        },
        {
            appNavController.navigate(AppScreens.PublicarCamion.screenName)
        },
        {
            appNavController.navigate(AppScreens.PublicarMaquinaria.screenName)
        },
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
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = modifier.fillMaxSize()
        ) {
            item{
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().height(150.dp)
                ){
                    Text(
                        text = "Tipo del vehículo\na publicar",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                }
            }

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

@Composable
fun componenteImagen(
    imagen: ByteArray,
    onClickBorrarImagen: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier.padding(8.dp)
        ) {
            // Imagen
            imagenByteArray(
                imagen,
                modifier = Modifier.fillMaxWidth()
            )

            // Botón de borrado
            IconButton (
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = { onClickBorrarImagen() },
                modifier = Modifier.fillMaxHeight().align(Alignment.TopEnd).size(50.dp).padding(6.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }

    }
}

