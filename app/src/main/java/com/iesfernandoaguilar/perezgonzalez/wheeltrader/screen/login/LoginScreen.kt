package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screen.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.customColorLight
import kotlinx.coroutines.launch
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    conectionViewModel: ConectionViewModel = viewModel(),
    loginViewModel: LoginViewModel = viewModel(),
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
) {

    val loginUiState by loginViewModel.uiState.collectAsState()
    val conectionUiState by conectionViewModel.uiState.collectAsState()

    val properties = Properties()
    val assetManager = context.assets

    properties.load(InputStreamReader(assetManager.open("conf.properties")))

    conectionViewModel.viewModelScope.launch {
        val address: String = properties.getProperty("ADDRESS")
        val port: Int = Integer.parseInt(properties.getProperty("PORT"))

        conectionViewModel.conectar(address, port)
        Log.d("Login", "Conectando al servidor: " + conectionUiState.socket?.isConnected)

        conectionViewModel.escucharDelServidor_Login(loginUiState.currentNombreUsuario, loginUiState.currentContrasenia)
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
                            Log.d("Login", "Nombre usuario: ${loginUiState.currentNombreUsuario}")
                            Log.d("Login", "Contraseña: ${loginUiState.currentContrasenia}")

                            conectionViewModel.viewModelScope.launch {
                                Log.d("Login", "Conexión: " + conectionUiState.socket?.port)
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