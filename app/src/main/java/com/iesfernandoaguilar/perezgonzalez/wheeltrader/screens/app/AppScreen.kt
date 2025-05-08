package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.model.Usuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.ListaAnuncios
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.confUsuario.ConfUsuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.TiposFiltros
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodoViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.ListaTiposVehiculos
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.PublicarCoche
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class AppScreens(val screenName: String){
    Home(screenName = "home"),

    ConfUsu(screenName = "conf_usu"),

    ListaTiposPublicar(screenName = "lista_tipos_publicar"),
    PublicarCoche(screenName = "publicar_coche"),

    Notificaciones(screenName = "notificaciones"),

    TipoFiltros(screenName = "tipo_filtros"),
    FiltroTodo(screenName = "filtro_todo"),

    ListaAnuncios(screenName = "lista_anuncios")
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun AppScreen(
    context: Context,
    navController: NavHostController,
    appNavController: NavHostController = rememberNavController(),
    conectionViewModel: ConectionViewModel,
    appViewModel: AppViewModel = viewModel(
        factory = AppViewModelFactory(conectionViewModel),
    ),
    filtrosViewModel: FiltrosViewModel = viewModel(),
    filtroTodoViewModel: FiltroTodoViewModel = viewModel(),
    modifier: Modifier = Modifier
){
    /*val scope = rememberCoroutineScope()
    val job = remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(Unit) {
        job.value = scope.launch(Dispatchers.IO) {
            while (isActive){
                appViewModel.confVM(context)
                appViewModel.showMsg = { context, msg ->
                    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                }
                appViewModel.escucharDelServidor_App()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            job.value?.cancel()
        }
    }*/

    val conectionUiState by conectionViewModel.uiState.collectAsState()
    val filtrosUiState by filtrosViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            appViewModel.confVM(context)
            appViewModel.showMsg = { context, msg ->
                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
            }
        }
        appViewModel.escucharDelServidor_App()
    }

    DisposableEffect(Unit) {
        onDispose {
            Log.d("App", "Se cierra el hilo principal")
            appViewModel.pararEscuchaServidor_App()
        }
    }

    Scaffold(
        topBar = {
            mainAppBar()
        },
        bottomBar = {
            mainBottomBar(
                confUsuarioOnClick = {
                    appNavController.navigate(AppScreens.ConfUsu.screenName)
                },
                homeOnClick = {
                    appNavController.navigate(AppScreens.Home.screenName)
                },
                notificacionesOnClick = {},
                publicarAnuncioOnClick = {
                    appNavController.navigate(AppScreens.ListaTiposPublicar.screenName)
                }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = appNavController,
            startDestination = AppScreens.Home.screenName,
            modifier = modifier.fillMaxSize().padding(innerPadding)
        ){
            composable(route = AppScreens.Home.screenName){
                HomeScreen(
                    filterButtonOnClick = { appNavController.navigate(AppScreens.TipoFiltros.screenName) }
                )
            }

            composable(route = AppScreens.TipoFiltros.screenName){
                TiposFiltros(
                    filtrosViewModel = filtrosViewModel,
                    appNavController = appNavController
                )
            }

            composable(route = AppScreens.FiltroTodo.screenName){
                FiltroTodo(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtroTodoViewModel = filtroTodoViewModel,
                    appViewModel = appViewModel,
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.ListaAnuncios.screenName){
                ListaAnuncios(
                    appViewModel = appViewModel,
                    filtrosUiState = filtrosUiState,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.ConfUsu.screenName){
                ConfUsuario(
                    conectionUiState = conectionUiState,
                    conectionViewModel = conectionViewModel,
                    onClickCerrarSesion = {
                        conectionViewModel.cerrarSesion()
                        navController.navigate(WheelTraderScreens.Login.screenName)
                    }
                )
            }

            composable(route = AppScreens.Notificaciones.screenName){
                // TODO: Hacer la pantalla de Notificaciones
            }

            composable(route = AppScreens.ListaTiposPublicar.screenName){
                ListaTiposVehiculos(
                    appViewModel = appViewModel,
                    appNavController = appNavController
                )
            }

            composable(route = AppScreens.PublicarCoche.screenName){
                PublicarCoche(
                    appViewModel = appViewModel
                )
            }
        }
    }
}