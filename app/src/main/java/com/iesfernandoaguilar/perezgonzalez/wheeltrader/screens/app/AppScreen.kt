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
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodo
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.FiltrosViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.TiposFiltros
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.filtroTodo.FiltroTodoViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class AppScreens(val screenName: String){
    Home(screenName = "home"),
    ConfUsu(screenName = "conf_usu"),
    PubAnuncio(screenName = "pub_anuncio"),
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
    val appUiState by appViewModel.uiState.collectAsState()
    val conectionUiState by conectionViewModel.uiState.collectAsState()

    appViewModel.changeGoToListaAnuncios(false)

    /*LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            appViewModel.confFlujos(conectionUiState.input, conectionUiState.output, context)
            appViewModel.escucharDelServidor_App()
        }
    }*/

    appViewModel.viewModelScope.launch(Dispatchers.IO) {
        appViewModel.confFlujos(conectionUiState.input, conectionUiState.output, context)
        appViewModel.escucharDelServidor_App()
    }

    LaunchedEffect(appUiState.goToListaAnuncios) {
        if(appUiState.goToListaAnuncios){
            Log.d("App", "Va a lista anuncios")
            appNavController.navigate(AppScreens.ListaAnuncios.screenName)
        }
    }

    Scaffold(
        topBar = {
            mainAppBar()
        },
        bottomBar = {
            mainBottomBar()
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

            composable(route = AppScreens.ConfUsu.screenName){
                // TODO: Hacer la pantalla de Conf. Usuario
            }

            composable(route = AppScreens.Notificaciones.screenName){
                // TODO: Hacer la pantalla de Notificaciones
            }

            composable(route = AppScreens.PubAnuncio.screenName){
                // TODO: Hacer la pantalla de Publicar Anuncio
            }

            composable(route = AppScreens.TipoFiltros.screenName){
                TiposFiltros(
                    // appViewModel = appViewModel,
                    filtrosViewModel = filtrosViewModel,
                    appNavController = appNavController
                )
            }

            composable(route = AppScreens.FiltroTodo.screenName){
                FiltroTodo(
                    filtroTodoViewModel = filtroTodoViewModel,
                    appViewModel = appViewModel
                )
            }
        }
    }
}