package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.R
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionUiState
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContrasenia1
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContrasenia2
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContrasenia3
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContraseniaViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.recuperarContrasenia.RecuperarContraseniaViewModelFactory
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg1Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg2Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg3Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.registro.Reg4Screen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.customColorLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class LoginScreens(val screenName: String){
    Login(screenName = "login"),

    Reg1(screenName = "reg1"),
    Reg2(screenName = "reg2"),
    Reg3(screenName = "reg3"),
    Reg4(screenName = "reg4"),

    Admin(screenName = "admin"),

    RecuperarContrasenia1(screenName = "recuperar_contrasenia1"),
    RecuperarContrasenia2(screenName = "recuperar_contrasenia2"),
    RecuperarContrasenia3(screenName = "recuperar_contrasenia3")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun LoginScreen(
    context: Context,
    navController: NavHostController,
    loginNavController: NavHostController = rememberNavController(),
    conectionViewModel: ConectionViewModel,
    loginViewModel: LoginViewModel,
    recuperarContraseniaViewModel: RecuperarContraseniaViewModel = viewModel(
        factory = RecuperarContraseniaViewModelFactory(conectionViewModel = conectionViewModel)
    ),
    modifier: Modifier = Modifier
) {
    val loginUiState by loginViewModel.uiState.collectAsState()
    val conectionUiState by conectionViewModel.uiState.collectAsState()
    val recuperarContraseniaUiState by recuperarContraseniaViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            loginViewModel.confVM(context)
            loginViewModel.showMsg = { context, msg ->
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect(conectionUiState.socket) {
        if(conectionUiState.socket != null && !conectionUiState.socket!!.isClosed){
            loginViewModel.escucharDelServidor_Login()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            loginViewModel.pararEscuchaServidor_Login()
        }
    }

    // Controla que al iniciar sesión se cambie de ventana y se pare el hilo
    LaunchedEffect (conectionUiState.usuario) {
        if(conectionUiState.usuario != null){
            conectionUiState.usuario?.let {
                if("USUARIO".equals(conectionUiState.usuario!!.rol)){
                    navController.navigate(WheelTraderScreens.App.screenName) {
                        popUpTo(WheelTraderScreens.Login.screenName) { inclusive = true }
                    }
                }else{
                    loginNavController.navigate(LoginScreens.Admin.screenName)
                }
                loginViewModel.limpiarInicioSesion()
            }
        }
    }

    Scaffold(
        topBar = {
            mainAppBar()
        },
    ) { innerPadding ->
        NavHost(
            navController = loginNavController,
            startDestination = LoginScreens.Login.screenName,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
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
                    loginNavController = loginNavController,
                    loginUiState = loginUiState,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = LoginScreens.Reg2.screenName){
                Reg2Screen(
                    loginNavController = loginNavController,
                    loginUiState = loginUiState,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = LoginScreens.Reg3.screenName){
                Reg3Screen(
                    loginNavController = loginNavController,
                    loginUiState = loginUiState,
                    loginViewModel = loginViewModel
                )
            }

            composable(route = LoginScreens.Reg4.screenName){
                Reg4Screen(
                    loginNavController = loginNavController
                )
            }

            composable(route = LoginScreens.Admin.screenName){
                AdminFailScreen(
                    onClickCerrarSesion = {
                        conectionViewModel.cerrarSesion()
                        navController.navigate(LoginScreens.Login.screenName)
                    }
                )
            }

            composable(route = LoginScreens.RecuperarContrasenia1.screenName){
                RecuperarContrasenia1(
                    loginViewModel = loginViewModel,
                    loginUiState = loginUiState,
                    recuperarContraseniaUiState = recuperarContraseniaUiState,
                    recuperarContraseniaViewModel = recuperarContraseniaViewModel,
                    goToCodigo = {
                        loginNavController.navigate(LoginScreens.RecuperarContrasenia2.screenName)
                    }
                )
            }

            composable(route = LoginScreens.RecuperarContrasenia2.screenName){
                RecuperarContrasenia2(
                    loginViewModel = loginViewModel,
                    loginUiState = loginUiState,
                    recuperarContraseniaUiState = recuperarContraseniaUiState,
                    recuperarContraseniaViewModel = recuperarContraseniaViewModel,
                    goToReiniciarContrasenia = {
                        loginNavController.navigate(LoginScreens.RecuperarContrasenia3.screenName)
                    }
                )
            }

            composable(route = LoginScreens.RecuperarContrasenia3.screenName){
                RecuperarContrasenia3(
                    loginViewModel = loginViewModel,
                    loginUiState = loginUiState,
                    recuperarContraseniaUiState = recuperarContraseniaUiState,
                    recuperarContraseniaViewModel = recuperarContraseniaViewModel,
                    goToLogin = {
                        loginNavController.navigate(LoginScreens.Login.screenName)
                    }
                )
            }
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
    var pwVisible by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround,
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
            modifier = Modifier.weight(0.2F)
        ) {
            Text(
                text = stringResource(R.string.texto_bienvenido),
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.texto_iniciar_sesion),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    OutlinedTextField(
                        value = loginUiState.currentNombreUsuario,
                        onValueChange = { loginViewModel.onNombreUsuarioChange(it) },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.texto_campo_correo_nomusu),
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
                                text = stringResource(R.string.texto_contrasena),
                                color = Color(0x00FF1c1c1c)
                            )
                        },
                        visualTransformation = if (pwVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            Image(
                                painter = if(pwVisible) painterResource(R.drawable.iconopwvisible) else painterResource(R.drawable.iconopwnovisible),
                                contentDescription = stringResource(R.string.icono_contrasena_visible_no_visible),
                                modifier = Modifier.clickable { pwVisible = !pwVisible }
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
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_iniciar_sesion),
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = {
                            loginNavController.navigate(LoginScreens.Reg1.screenName)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                    ) {
                        Text(
                            text = stringResource(R.string.texto_registrarse),
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
                text = stringResource(R.string.texto_recuperar_contrasena),
                style = MaterialTheme.typography.labelMedium,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    loginNavController.navigate(LoginScreens.RecuperarContrasenia1.screenName)
                },
                color = Color.White
            )
        }
    }
}