package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Reg1Screen(
    loginNavController: NavController,
    loginUiState: LoginUiState,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
){
    loginViewModel.reiniciarGoToPaso2()

    LaunchedEffect(loginUiState.goToPaso2) {
        if(loginUiState.goToPaso2){
            loginNavController.navigate(LoginScreens.Reg2.screenName)
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
                painter = painterResource(R.drawable.barrareg1),
                contentDescription = stringResource(R.string.desc_imagen_barra_registro_1),
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
                text = stringResource(R.string.texto_datos_personales),
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
                    textField(placeHolder = "Nombre", value = loginUiState.nombre, onValueChange = { loginViewModel.onNombreChange(it) })

                    textField(placeHolder = "Apellidos", value = loginUiState.apellidos, onValueChange = { loginViewModel.onApellidosChange(it)})

                    textField(placeHolder = "DNI", value = loginUiState.dni, onValueChange = { loginViewModel.onDniChange(it) })

                    textField(placeHolder = "Dirección", value = loginUiState.direccion, onValueChange = { loginViewModel.onDireccionChange(it) })
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
                                loginUiState.nombre.length < 1 ||
                                loginUiState.apellidos.length < 1 ||
                                loginUiState.dni.length < 1 ||
                                loginUiState.direccion.length < 1
                            ) {
                                loginViewModel.mostrarToast("Debe rellenar todos los campos")
                            } else if (!loginUiState.dni.matches(Regex("^[0-9]{8}[A-Z]$"))) {
                                loginViewModel.mostrarToast("El formato del dni es incorrecto")
                            } else {
                                loginViewModel.comprobarDNI(loginUiState.dni)
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
                        loginViewModel.limpiarRegistro()
                        loginNavController.navigate(LoginScreens.Login.screenName)
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
                            text = stringResource(R.string.texto_volver),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun textField(
    placeHolder: String,
    value: String,
    onValueChange: (String) -> Unit
){
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeHolder,
                color = Color.LightGray
            )
        },
        enabled = true,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,

            cursorColor = Color.White,

            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,

            focusedLabelColor = Color.White,
            unfocusedLabelColor = Color.White,

            unfocusedIndicatorColor = Color.White,
            focusedIndicatorColor = Color.White,
            disabledIndicatorColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

/*@Preview
@Composable
fun reg1ScreenPreview(){
    WheelTraderTheme {
        Reg1Screen()
    }
}*/