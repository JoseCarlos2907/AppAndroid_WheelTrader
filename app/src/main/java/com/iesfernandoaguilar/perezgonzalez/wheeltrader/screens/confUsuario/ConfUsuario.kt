package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.confUsuario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel

@Composable
fun ConfUsuario(
    conectionUiState: ConectionUiState,
    conectionViewModel: ConectionViewModel,
    modifier: Modifier = Modifier
){
    var acordeon1 by remember { mutableStateOf(false) }
    var acordeon2 by remember { mutableStateOf(false) }
    var acordeon3 by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Row(
                modifier = Modifier.clickable(onClick = { acordeon1 = !acordeon1 }).fillMaxWidth().padding(16.dp)
            ) {
                Icon(
                    imageVector = if (acordeon1) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.White
                )

                Text(
                    text = "Datos Personales",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            AnimatedVisibility(visible = acordeon1) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "Nombre: ${conectionUiState.usuario?.nombre}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Text(
                        text = "Apellidos: ${conectionUiState.usuario?.apellidos}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Text(
                        text = "DNI: ${conectionUiState.usuario?.dni}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Text(
                        text = "Dirección: ${conectionUiState.usuario?.direccion}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier.clickable(onClick = { acordeon2 = !acordeon2 }).fillMaxWidth().padding(16.dp)
            ) {
                Icon(
                    imageVector = if (acordeon2) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.White
                )

                Text(
                    text = "Datos de Cuenta",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            AnimatedVisibility(visible = acordeon2) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "Nombre de Usuario: ${conectionUiState.usuario?.nombreUsuario}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Text(
                        text = "Correo: ${conectionUiState.usuario?.correo}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )

                    Text(
                        text = "Correo de PayPal: ${conectionUiState.usuario?.correoPP}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }

            Row(
                modifier = Modifier.clickable(onClick = { acordeon3 = !acordeon3 }).fillMaxWidth().padding(16.dp)
            ) {
                Icon(
                    imageVector = if (acordeon3) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "",
                    tint = Color.White
                )

                Text(
                    text = "Contraseña",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }

            AnimatedVisibility(visible = acordeon3) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    Text(
                        text = "TODO: Campos de contraseña",
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.White
                    )
                }
            }
        }

        Column{
            boton({}, "Mis Valoraciones")
            boton({}, "Mis Anuncios")
            boton({}, "Mis Guardados")
            boton({}, "Mis Pagos")
            boton({}, "Mis Compras")
            boton({}, "Mis Reuniones")
        }
    }
}

@Composable
private fun boton(
    onClick: () -> Unit,
    texto: String
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp)
    ) {
        Button(
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = texto,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Black
            )
        }
    }
}