package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.tipoVehiculoCard
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme

@Composable
fun TiposFiltros(
    filtrosViewModel: FiltrosViewModel,
    // appViewModel: AppViewModel,
    appNavController: NavHostController,
    modifier: Modifier = Modifier
) {
    val opciones = listOf("Cualquiera", "Coches", "Motos", "Camionetas", "Camiones", "Maquinaria")
    val imagenes = listOf(R.drawable.cualquiera, R.drawable.fotofiltrocoche, R.drawable.fotofiltromoto, R.drawable.fotofiltrocamioneta, R.drawable.fotofiltrocamion, R.drawable.fotofiltromaquinaria)
    val onClicks = listOf(
        {
            filtrosViewModel.asignarTipoFiltro("Todo")
            appNavController.navigate(AppScreens.FiltroTodo.screenName)
        },
        {
            filtrosViewModel.asignarTipoFiltro("Coche")
            appNavController.navigate(AppScreens.FiltroCoche.screenName)
        },
        {
            filtrosViewModel.asignarTipoFiltro("Moto")
            appNavController.navigate(AppScreens.FiltroMoto.screenName)
        },
        {
            filtrosViewModel.asignarTipoFiltro("Camioneta")
            appNavController.navigate(AppScreens.FiltroCamioneta.screenName)
        },
        {
            filtrosViewModel.asignarTipoFiltro("Camion")
            appNavController.navigate(AppScreens.FiltroCamion.screenName)
        },
        {
            filtrosViewModel.asignarTipoFiltro("Maquinaria")
            appNavController.navigate(AppScreens.FiltroMaquinaria.screenName)
        },
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
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

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    WheelTraderTheme {
        // TiposFiltros()
    }
}