package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel

@Composable
fun HomeScreen(
    conectionViewModel: ConectionViewModel,
    modifier: Modifier = Modifier
) {
    val conectionUiState by conectionViewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            text = "Bienvenido, ${conectionUiState.usuario?.nombreUsuario ?: "_NOMBRE_"}"
        )
    }
}