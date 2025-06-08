package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.textField
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.utils.SecureUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Base64

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RecuperarContrasenia3(
    loginViewModel: LoginViewModel,
    loginUiState: LoginUiState,
    goToLogin: () -> Unit,
    recuperarContraseniaViewModel: RecuperarContraseniaViewModel,
    recuperarContraseniaUiState: RecuperarContraseniaUiState,
    modifier: Modifier = Modifier
){
    LaunchedEffect(Unit) {
        loginViewModel.asignarGoToReiniciarContrasenia(false)
    }

    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().background(
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
            modifier = Modifier.fillMaxSize().weight(0.1f)
        ) {
            Text(
                text = stringResource(R.string.texto_reiniciar_contrasena),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().weight(0.55f)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                modifier = Modifier.fillMaxSize().padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(0.7f).fillMaxSize()
                    ) {
                        textField(placeHolder = "Contraseña", value = recuperarContraseniaUiState.contrasenia, onValueChange = { recuperarContraseniaViewModel.onContraseniaChange(it) })

                        textField(placeHolder = "Confirmar contraseña", value = recuperarContraseniaUiState.repetirContrasenia, onValueChange = { recuperarContraseniaViewModel.onRepetirContraseniaChange(it) })
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

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().weight(0.25f)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Button(
                    onClick = {
                        var bien = false
                        recuperarContraseniaViewModel.viewModelScope.launch(Dispatchers.IO) {
                            if (!recuperarContraseniaUiState.contrasenia.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{6,}$"))) {
                                loginViewModel.mostrarToast("La contraseña no cumple los requisitos mínimos")
                            } else if (recuperarContraseniaUiState.contrasenia != recuperarContraseniaUiState.repetirContrasenia) {
                                loginViewModel.mostrarToast("Las contraseñas no coinciden")
                            } else {
                                loginViewModel.recuperarContrasenia_Contrasenias(SecureUtils.generate512(recuperarContraseniaUiState.contrasenia, Base64.getDecoder().decode(loginUiState.saltUsuario)))
                                bien = true
                            }
                        }

                        if(bien){
                            goToLogin()
                        }

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(200.dp).height(40.dp)

                ) {
                    Text(
                        text = stringResource(R.string.texto_reiniciar_contrasena),
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Black
                    )
                }
            }
        }

    }
}