package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.material.snackbar.Snackbar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.customColorLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    context: Context,
    navController: NavHostController,
    conectionViewModel: ConectionViewModel,
    loginViewModel: LoginViewModel = LoginViewModel(
        conectionViewModel = conectionViewModel
    ),
    modifier: Modifier = Modifier
) {

    val loginUiState by loginViewModel.uiState.collectAsState()
    val conectionUiState by conectionViewModel.uiState.collectAsState()

    loginViewModel.viewModelScope.launch(Dispatchers.IO) {
        loginViewModel.confFlujos(conectionUiState.input, conectionUiState.output, context)
        loginViewModel.onError = { context, msg ->
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
        loginViewModel.escucharDelServidor_Login()
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.2F)
        ) {
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.6F)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.fillMaxSize().padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize().padding(8.dp)
                ) {
                    Text(
                        text = "Iniciar Sesión",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0x00FF1c1c1c)
                    )

                    OutlinedTextField(
                        value = loginUiState.currentNombreUsuario,
                        onValueChange = { loginViewModel.onNombreUsuarioChange(it) },
                        placeholder = {
                            Text(
                                text = "Correo o nombre de usuario",
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
                        shape = RoundedCornerShape(22.dp)
                    )

                    OutlinedTextField(
                        value = loginUiState.currentContrasenia,
                        onValueChange = { loginViewModel.onContraseniaChange(it) },
                        placeholder = {
                            Text(
                                text = "Contraseña",
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
                        shape = RoundedCornerShape(22.dp)
                    )

                    Button(
                        onClick = {
                            // Lanzarlo en segundo plano
                            loginViewModel.viewModelScope.launch(Dispatchers.IO) {
                                // Proceso de comunicación entre la apliación y el servidor
                                loginViewModel.iniciarSesion(loginUiState.currentNombreUsuario)
                                // Espera a que llegue el objeto usuario
                                Thread.sleep(3000)
                                // Ejecuto la navegación en el hilo principal en caso de que el usuario se haya encontado en la base de datos
                                withContext(Dispatchers.Main){
                                    if(conectionUiState.usuario != null){
                                        navController.navigate(WheelTraderScreens.Home.screenName)
                                    }
                                }
                            }


                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color(0xFF1c1c1c)
                        )
                    }

                    Button(
                        onClick = {
                            Log.d("Login", "Registrarse: Sin función")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = customColorLight)
                    ) {
                        Text(
                            text = "Registrarse",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.2F)
        ) {
            Text(
                text = "Recuperar contraseña",
                style = MaterialTheme.typography.labelMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { Log.d("Login", "Recuperar Contraseña: Sin función") }
            )
        }
    }
}