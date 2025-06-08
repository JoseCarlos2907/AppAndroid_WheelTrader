package com.iesfernandoaguilar.perezgonzalez.wheeltrader

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app.AppViewModelFactory
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModelFactory
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.ui.theme.WheelTraderTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.util.Properties


enum class WheelTraderScreens(val screenName: String){
    Login(screenName = "login"),
    App(screenName = "app")
}


@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun wheeltraderApp(
    conectionViewModel: ConectionViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier
){
    val conectionUiState by conectionViewModel.uiState.collectAsState()

    val properties = Properties()

    val archivoConfConexion = File(context.filesDir, "wheel_trader_config.properties")

    if(!archivoConfConexion.exists()){
        archivoConfConexion.createNewFile()
    }

    properties.load(InputStreamReader(FileInputStream(archivoConfConexion)))

    val loginViewModel: LoginViewModel = viewModel(
        factory = LoginViewModelFactory(conectionViewModel = conectionViewModel)
    )

    val appViewModel: AppViewModel = viewModel(
        factory = AppViewModelFactory(conectionViewModel),
    )

    if(properties.containsKey("ADDRESS") && properties.containsKey("PORT")){
        conectionViewModel.setConfConexionExistente(true)
    }

    if(!conectionUiState.confConexionExistente){
        AlertConexion(
            onConnect = { direccion, puerto ->
                properties.setProperty("ADDRESS", direccion)
                properties.setProperty("PORT", puerto.toString())

                properties.store(FileOutputStream(archivoConfConexion.absolutePath), null)

                conectionViewModel.setConfConexionExistente(true)
            },
            onCancel = {
                (context as? Activity)?.finishAffinity()
            }
        )
        Log.d("WTApp", "Conexion no existente")
    }else{
        conectionViewModel.viewModelScope.launch(Dispatchers.IO) {
            Log.d("WTApp", "Conecta")
            conectionViewModel.conectar(properties.getProperty("ADDRESS"), Integer.parseInt(properties.getProperty("PORT")))
        }
    }


    NavHost(
        navController = navController,
        startDestination = WheelTraderScreens.Login.screenName,
        modifier = modifier
    ){
        composable(route = WheelTraderScreens.Login.screenName){
            LoginScreen(
                context = context,
                conectionViewModel = conectionViewModel,
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(route = WheelTraderScreens.App.screenName) {
            AppScreen(
                context = context,
                conectionViewModel = conectionViewModel,
                navController = navController,
                appViewModel = appViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun mainAppBar(
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.logoazul),
                    contentDescription = stringResource(R.string.desc_imagen_logo_barra_superior),
                    modifier = Modifier.height(54.dp),
                )
            }
        },
        navigationIcon = {},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    )
}

@Composable
fun mainBottomBar(
    confUsuarioOnClick: () -> Unit,
    homeOnClick: () -> Unit,
    notificacionesOnClick: () -> Unit,
    publicarAnuncioOnClick: () -> Unit,
    modifier: Modifier = Modifier
){
    val opciones = listOf("Conf. Usuario", "Home", "Notificaciones", "Publicar Anuncio")
    val iconos = listOf(R.drawable.iconoconfusuarioazul, R.drawable.iconohomeazul, R.drawable.icononotificacionazul, R.drawable.iconoaniadirazul)
    val onclicks = listOf(confUsuarioOnClick, homeOnClick, notificacionesOnClick, publicarAnuncioOnClick)

    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = Modifier.height(100.dp)
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            opciones.forEach{item->
                NavigationBarItem(
                    selected = false,
                    onClick = onclicks.get(opciones.indexOf(item)),
                    icon = {
                        Image(
                            painter = painterResource(iconos.get(opciones.indexOf(item))),
                            contentDescription = stringResource(R.string.desc_imagen_menu_inferior),
                            modifier = Modifier
                                .height(30.dp)
                                .width(30.dp)
                        )
                    },
                    modifier = Modifier.width(40.dp)
                )
            }
        }
    }
}

@Composable
fun AlertConexion(
    onConnect: (String, Int) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var direccion by remember { mutableStateOf("") }
    var puerto by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = stringResource(R.string.texto_conf_servidor)
            )
        },
        text = {
            Column {
                OutlinedTextField(
                    value = direccion,
                    onValueChange = { direccion = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_direccion),
                            color = Color(0x00FF1c1c1c)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    value = puerto,
                    onValueChange = { puerto = it },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.texto_puerto),
                            color = Color(0x00FF1c1c1c)
                        )
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (!direccion.isEmpty() && !puerto.isEmpty()) {
                        onConnect(direccion, puerto.toInt())
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.texto_conectar),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            Button(
                onClick = { onCancel() },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = stringResource(R.string.texto_cancelar),
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.White
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        modifier = modifier.height(500.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun previewBottomBar() {
    WheelTraderTheme {
        // mainBottomBar()
    }
}