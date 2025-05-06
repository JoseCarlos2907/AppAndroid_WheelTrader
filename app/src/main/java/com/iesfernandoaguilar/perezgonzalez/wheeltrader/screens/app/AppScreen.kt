package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
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
    appViewModel: AppViewModel = AppViewModel(
        conectionViewModel = conectionViewModel
    ),
    filtrosViewModel: FiltrosViewModel = FiltrosViewModel(),
    filtroTodoViewModel: FiltroTodoViewModel = FiltroTodoViewModel(),
    modifier: Modifier = Modifier
){
    val conectionUiState by conectionViewModel.uiState.collectAsState()
    val filtrosUiState by filtrosViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            appViewModel.confVM(context)
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
                    filtrosUiState = filtrosUiState
                )
            }

            composable(route = AppScreens.ConfUsu.screenName){
                ConfUsuario(
                    conectionUiState = conectionUiState,
                    conectionViewModel = conectionViewModel
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