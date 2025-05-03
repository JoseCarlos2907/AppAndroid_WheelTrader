package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.tipoVehiculoCard
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme

@Composable
fun TiposFiltros(
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Cualquiera", "Coches", "Motos", "Camionetas", "Camiones", "Maquinaria")
    val imagenes = listOf(R.drawable.cualquiera, R.drawable.fotofiltrocoche, R.drawable.fotofiltromoto, R.drawable.fotofiltrocamioneta, R.drawable.fotofiltrocamion, R.drawable.fotofiltromaquinaria)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(opciones) { opcion ->
            tipoVehiculoCard(
                imagenes.get(opciones.indexOf(opcion)),
                opcion
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    WheelTraderTheme {
        TiposFiltros()
    }
}