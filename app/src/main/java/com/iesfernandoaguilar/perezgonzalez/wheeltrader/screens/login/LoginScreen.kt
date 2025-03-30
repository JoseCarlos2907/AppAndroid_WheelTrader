package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg1Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg2Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg3Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg4Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.customColorLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class LoginScreens(val screenName: String){
    Login(screenName = "login"),
    Reg1(screenName = "reg1"),
    Reg2(screenName = "reg2"),
    Reg3(screenName = "reg3"),
    Reg4(screenName = "reg4")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    context: Context,
    navController: NavHostController,
    loginNavController: NavHostController = rememberNavController(),
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

    NavHost(
        navController = loginNavController,
        startDestination = LoginScreens.Login.screenName,
        modifier = modifier.fillMaxSize()
    ){
        composable(route = LoginScreens.Login.screenName){
            LoginForm(
                navController = navController,
                loginNavController = loginNavController,
                conectionUiState = conectionUiState,
                loginUiState = loginUiState,
                loginViewModel = loginViewModel,
                modifier = modifier
            )
        }

        composable(route = LoginScreens.Reg1.screenName){
            Reg1Screen(
                loginNavController = loginNavController
            )
        }

        composable(route = LoginScreens.Reg2.screenName){
            Reg2Screen(
                loginNavController = loginNavController
            )
        }

        composable(route = LoginScreens.Reg3.screenName){
            Reg3Screen(
                loginNavController = loginNavController
            )
        }

        composable(route = LoginScreens.Reg4.screenName){
            Reg4Screen(
                loginNavController = loginNavController
            )
        }
    }

}

@Composable
fun LoginForm(
    navController: NavHostController,
    loginNavController: NavHostController,
    conectionUiState: ConectionUiState,
    loginUiState: LoginUiState,
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
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
            modifier = Modifier.weight(0.2F)
        ) {
            Text(
                text = "Bienvenido",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(0.6F)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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
                        color = MaterialTheme.colorScheme.primary
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
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = "Iniciar Sesión",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {
                            loginNavController.navigate(LoginScreens.Reg1.screenName)
                            // Log.d("Login", "Registrarse: Sin función")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
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
                modifier = Modifier.clickable { Log.d("Login", "Recuperar Contraseña: Sin función") },
                color = Color.White
            )
        }
    }
}