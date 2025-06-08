package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.reporte

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Reporte
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ReporteScreen(
    appViewModel: AppViewModel,
    reporteViewModel: ReporteViewModel = viewModel(),
    conectionUiState: ConectionUiState,
    goToHome: () -> Unit,
    modifier: Modifier = Modifier
){

    val reporteUiState by reporteViewModel.uiState.collectAsState()
    val appUiState by appViewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        onDispose {
            appViewModel.reiniciarReporte()
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
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.2F)
                .fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.texto_reportar),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

        Row(
            modifier = Modifier.weight(0.6F)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.2F)
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            value = reporteUiState.motivo,
                            onValueChange = { reporteViewModel.onMotivoChange(it) },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.texto_motivo),
                                    color = Color(0x00FF1c1c1c)
                                )
                            },
                            enabled = true,
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                cursorColor = Color(0x00FF1c1c1c),
                                focusedTextColor = Color(0x00FF1c1c1c),
                                unfocusedTextColor = Color(0x00FF1c1c1c),
                                focusedLabelColor = Color(0x00FF1c1c1c),
                                unfocusedLabelColor = Color(0x00FF1c1c1c),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.5F)
                            .padding(8.dp)
                    ) {
                        OutlinedTextField(
                            value = reporteUiState.explicacion,
                            onValueChange = { reporteViewModel.onExplicacionChange(it) },
                            placeholder = {
                                Text(
                                    text = stringResource(R.string.texto_explicacion),
                                    color = Color(0x00FF1c1c1c)
                                )
                            },
                            enabled = true,
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                cursorColor = Color(0x00FF1c1c1c),
                                focusedTextColor = Color(0x00FF1c1c1c),
                                unfocusedTextColor = Color(0x00FF1c1c1c),
                                focusedLabelColor = Color(0x00FF1c1c1c),
                                unfocusedLabelColor = Color(0x00FF1c1c1c),
                                unfocusedIndicatorColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .verticalScroll(rememberScrollState())
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .weight(0.2F)
                .fillMaxSize()
        ) {
            Button(
                onClick = {
                    var reporte = Reporte(
                        motivo = reporteUiState.motivo,
                        explicacion = reporteUiState.explicacion,
                        usuarioEnvia = conectionUiState.usuario!!,
                        usuarioRecibe = appUiState.anuncioSeleccionado!!.vendedor!!
                    )

                    appViewModel.viewModelScope.launch(Dispatchers.IO) {
                        appViewModel.reportarUsuario(reporte)
                    }

                    goToHome()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.texto_reportar),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        }
    }
}