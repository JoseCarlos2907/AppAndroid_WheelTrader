package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro

import android.util.Log
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

@Composable
fun Reg2Screen(
    loginNavController: NavController,
    loginUiState: LoginUiState,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
){
    loginViewModel.reiniciarGoToPaso3()

    LaunchedEffect(loginUiState.goToPaso3) {
        if(loginUiState.goToPaso3){
            loginNavController.navigate(LoginScreens.Reg3.screenName)
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
                painter = painterResource(R.drawable.barrareg2),
                contentDescription = stringResource(R.string.desc_imagen_barra_registro_2),
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
                text = stringResource(R.string.texto_cuenta_de_usuario),
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
                    textField(placeHolder = "Nombre de Usuario", value = loginUiState.nombreUsuario, onValueChange = { loginViewModel.onNombreUsuarioRegistroChange(it) })

                    textField(placeHolder = "Correo", value = loginUiState.correo, onValueChange = { loginViewModel.onCorreoChange(it) })

                    textField(placeHolder = "Correo de Paypal", value = loginUiState.correoPP, onValueChange = { loginViewModel.onCorreoPPChange(it) })

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
                            if (
                                loginUiState.nombreUsuario.length < 1 ||
                                loginUiState.correo.length < 1 ||
                                loginUiState.correoPP.length < 1
                            ) {
                                loginViewModel.mostrarToast("Debe rellenar todos los campos")
                            } else if (!loginUiState.correo.matches(Regex("^[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.[a-zA-Z]{2,3}$"))) {
                                loginViewModel.mostrarToast("El formato del correo es incorrecto")
                            } else {
                                loginViewModel.comprobarNombreUsuYCorreo(loginUiState.nombreUsuario, loginUiState.correo)
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
                        loginNavController.navigate(LoginScreens.Reg1.screenName)
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
fun reg2ScreenPreview(){
    WheelTraderTheme {
        Reg2Screen()
    }
}*/