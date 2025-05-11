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
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.anuncios.ListaAnuncios
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.confUsuario.ConfUsuario
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.TiposFiltros
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamion.FiltroCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCamioneta.FiltroCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroCoche.FiltroCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMaquinaria.FiltroMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroMoto.FiltroMoto
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.ListaTiposVehiculos
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamion.PublicarCamion
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCamioneta.PublicarCamioneta
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarCoche.PublicarCoche
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMaquinaria.PublicarMaquinaria
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.publicar.publicarMoto.PublicarMoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

enum class AppScreens(val screenName: String){
    Home(screenName = "home"),

    ConfUsu(screenName = "conf_usu"),

    ListaTiposPublicar(screenName = "lista_tipos_publicar"),
    PublicarCoche(screenName = "publicar_coche"),
    PublicarMoto(screenName = "publicar_moto"),
    PublicarCamioneta(screenName = "publicar_camioneta"),
    PublicarCamion(screenName = "publicar_camion"),
    PublicarMaquinaria(screenName = "publicar_maquinaria"),

    Notificaciones(screenName = "notificaciones"),

    TipoFiltros(screenName = "tipo_filtros"),
    FiltroTodo(screenName = "filtro_todo"),
    FiltroCoche(screenName = "filtro_coche"),
    FiltroMoto(screenName = "filtro_moto"),
    FiltroCamioneta(screenName = "filtro_camioneta"),
    FiltroCamion(screenName = "filtro_camion"),
    FiltroMaquinaria(screenName = "filtro_maquinaria"),

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
                    appViewModel = appViewModel,
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCoche.screenName){
                FiltroCoche(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroMoto.screenName){
                FiltroMoto(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCamioneta.screenName){
                FiltroCamioneta(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroCamion.screenName){
                FiltroCamion(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
                    filtrosViewModel = filtrosViewModel
                )
            }

            composable(route = AppScreens.FiltroMaquinaria.screenName){
                FiltroMaquinaria(
                    buscarOnClick = { appNavController.navigate(AppScreens.ListaAnuncios.screenName) },
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
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarMoto.screenName){
                PublicarMoto(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarCamioneta.screenName){
                PublicarCamioneta(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarCamion.screenName){
                PublicarCamion(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }

            composable(route = AppScreens.PublicarMaquinaria.screenName){
                PublicarMaquinaria(
                    appNavController = appNavController,
                    appViewModel = appViewModel,
                    conectionUiState = conectionUiState
                )
            }
        }
    }
}