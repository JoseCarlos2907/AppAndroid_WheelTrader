package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Reg3Screen(
    loginNavController: NavController,
    loginUiState: LoginUiState,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
) {
    loginViewModel.reiniciarGoToPaso4()

    LaunchedEffect(loginUiState.goToPaso4) {
        if(loginUiState.goToPaso4){
            loginViewModel.limpiarRegistro()
            loginNavController.navigate(LoginScreens.Reg4.screenName)
        }
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
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
        // Imagen barra de progreso
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(0.15f)
                .padding(top = 50.dp, start = 35.dp, end = 35.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.barrareg3),
                contentDescription = stringResource(R.string.desc_imagen_barra_registro_3),
                modifier = Modifier.fillMaxSize()
            )
        }

        // Texto del paso
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.1f)
        ) {
            Text(
                text = stringResource(R.string.texto_contrasena),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        // Card
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.55f)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .weight(0.7f)
                            .fillMaxSize()
                    ) {
                        textField(placeHolder = "Contraseña", value = loginUiState.contrasenia, onValueChange = { loginViewModel.onContraseniaRegistroChange(it) })

                        textField(placeHolder = "Confirmar contraseña", value = loginUiState.confContra, onValueChange = { loginViewModel.onConfContraChange(it) })
                    }

                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top,
                        modifier = Modifier.weight(0.3f)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_requisitos_contrasena),
                            color = Color.White,
                            style = MaterialTheme.typography.labelMedium
                        )

                        Text(
                            text = stringResource(R.string.texto_minimo_6_caracteres),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = stringResource(R.string.texto_minimo_mayusculas_minusculas),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )

                        Text(
                            text = stringResource(R.string.texto_minimo_numeros),
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }

        // Botones
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.25f)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        loginViewModel.viewModelScope.launch(Dispatchers.IO) {
                            if (!loginUiState.contrasenia.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$"))) {
                                loginViewModel.mostrarToast("La contraseña no cumple los requisitos mínimos")
                            } else if (!loginUiState.contrasenia.equals(loginUiState.confContra)) {
                                loginViewModel.mostrarToast("Las contraseñas no coinciden")
                            } else {
                                loginViewModel.registrarUsuario()
                            }
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .width(150.dp)
                        .height(40.dp)

                ) {
                    Text(
                        text = stringResource(R.string.texto_siguiente),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                }

                Button(
                    onClick = {
                        loginNavController.navigate(LoginScreens.Reg2.screenName)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(150.dp)
                        .height(40.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = stringResource(R.string.texto_anterior),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

    }
}

/*@Preview
@Composable
fun reg3ScreenPreview(){
    WheelTraderTheme {
        Reg3Screen()
    }
}*/