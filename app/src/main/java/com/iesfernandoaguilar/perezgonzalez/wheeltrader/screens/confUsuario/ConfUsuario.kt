package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.confUsuario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroGuardados
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.filtros.FiltroPublicados
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.textField
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ConfUsuario(
    appViewModel: AppViewModel,
    conectionUiState: ConectionUiState,
    filtrosViewModel: FiltrosViewModel,
    appNavController: NavController,
    onClickCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
){
    val appUiState by appViewModel.uiState.collectAsState()

    var acordeon1 by remember { mutableStateOf(false) }
    var acordeon2 by remember { mutableStateOf(false) }
    var acordeon3 by remember { mutableStateOf(false) }

    LaunchedEffect(appUiState.saltUsuarioReinicio) {
        if(appUiState.saltUsuarioReinicio != null){
            appViewModel.viewModelScope.launch(Dispatchers.IO) {
                appViewModel.reiniciarContrasenia(
                    nombreUsuario = conectionUiState.usuario!!.nombreUsuario,
                    contrasenia = appUiState.contraseniaReiniciarContrasenia
                )
                appViewModel.vaciarSaltReinicio()
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(
            brush = Brush.linearGradient(
                colors = listOf(Color.Black, Color(0xFF525151)),
                start = Offset(0f, 0f),
                end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
            )
        ).verticalScroll(rememberScrollState())
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
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize().height(400.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(0.7f).fillMaxSize()
                    ) {
                        textField(placeHolder = "Contraseña", value = appUiState.contraseniaReiniciarContrasenia, onValueChange = { appViewModel.onContraseniaConfUsuarioChange(it) })

                        textField(placeHolder = "Confirmar contraseña", value = appUiState.repetirContraseniaReiniciarContrasenia, onValueChange = { appViewModel.onRepetirContraseniaConfUsuarioChange(it) })
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(
                            text = "Requisitos de la contraseña",
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = "· Mínimo 6 caracteres",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "· Mínimo 1 mayúscula y 1 minúscula",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = "· Mínimo 1 número",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }

                    Button(
                        onClick = {
                            appViewModel.viewModelScope.launch(Dispatchers.IO) {
                                if (!appUiState.contraseniaReiniciarContrasenia.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$"))) {
                                    appViewModel.mostrarToast("La contraseña no cumple los requisitos mínimos")
                                } else if (!appUiState.contraseniaReiniciarContrasenia.equals(appUiState.repetirContraseniaReiniciarContrasenia)) {
                                    appViewModel.mostrarToast("Las contraseñas no coinciden")
                                } else {
                                    appViewModel.obtenerSaltReinicio(conectionUiState.usuario!!.nombreUsuario)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier.width(220.dp).height(40.dp)
                    ) {
                        Text(
                            text = "Reiniciar Contraseña",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Column{

            boton({
                filtrosViewModel.asignarFiltro(FiltroPublicados(conectionUiState.usuario!!.nombreUsuario))
                appNavController.navigate(AppScreens.ListaAnuncios.screenName)
            }, "Mis Anuncios")

            boton({
                filtrosViewModel.asignarFiltro(FiltroGuardados(conectionUiState.usuario!!.nombreUsuario))
                appNavController.navigate(AppScreens.ListaAnuncios.screenName)
            }, "Mis Guardados")

            boton({

            }, "Mis Pagos")

            boton({

            }, "Mis Compras")

            /*boton({

            }, "Mis Reuniones")*/
        }

        Row (
            modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp)
        ){
            Button(
                onClick = { onClickCerrarSesion() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }

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