package com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.app

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.WheelTraderScreens
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainAppBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.mainBottomBar
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.ConectionViewModel
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.filtros.TiposFiltros
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.home.HomeScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginScreen
import com.iesfernandoaguilar.perezgonzalez.wheeltrader.screens.login.LoginViewModel

enum class AppScreens(val screenName: String){
    Home(screenName = "home"),
    ConfUsu(screenName = "conf_usu"),
    PubAnuncio(screenName = "pub_anuncio"),
    Notificaciones(screenName = "notificaciones"),
    TipoFiltros(screenName = "tipo_filtros")
}

@Composable
fun AppScreen(
    context: Context,
    navController: NavHostController,
    appNavController: NavHostController = rememberNavController(),
    conectionViewModel: ConectionViewModel,
    modifier: Modifier = Modifier
){
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
                TiposFiltros()
            }
        }
    }
}